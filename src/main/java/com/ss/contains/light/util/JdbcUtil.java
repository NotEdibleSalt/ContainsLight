package com.ss.contains.light.util;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.ss.contains.light.common.paging.PagingParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.convert.MappingR2dbcConverter;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.mapping.R2dbcMappingContext;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 封装查询工具
 * 支持分页查询和查询集合对象
 *
 * @author NotEdibleSalt
 * @version 1.0
 * @date 2022-8-4
 */
@Slf4j
public class JdbcUtil {

    public StringBuilder sqlBuilder;

    public static JdbcUtil of(String sql) {

        if (StrUtil.isBlank(sql)) {
            throw new RuntimeException("sql不能为空");
        }
        JdbcUtil jdbcUtil = new JdbcUtil();
        jdbcUtil.sqlBuilder = new StringBuilder(sql);

        return jdbcUtil;
    }

    /**
     * 返回单个对象
     *
     * @param jdbcTemplate
     * @param tClass 返回值类型
     * @return reactor.core.publisher.Mono<T>
     */
    public <T> Mono<T> singleForObject(R2dbcEntityTemplate jdbcTemplate, Class<T> tClass) {

        printSql();
        return jdbcTemplate.getDatabaseClient()
                .sql(sqlBuilder.toString())
                .map(row -> new MappingR2dbcConverter(new R2dbcMappingContext()).read(tClass, row))
                .one();
    }


    /**
     * 返回一个map
     *
     * @param jdbcTemplate
     * @return reactor.core.publisher.Mono<java.util.Map<java.lang.String,java.lang.Object>>
     */
    public Mono<Map<String, Object>> singleForMap(R2dbcEntityTemplate jdbcTemplate) {

        printSql();
        return jdbcTemplate.getDatabaseClient()
                .sql(sqlBuilder.toString())
                .fetch()
                .one();
    }


    /**
     * 返回集合对象
     *
     * @param jdbcTemplate
     * @param tClass 返回值类型
     * @return reactor.core.publisher.Flux<T>
     */
    public <T> Flux<T> list(R2dbcEntityTemplate jdbcTemplate, Class<T> tClass) {

        printSql();
        return jdbcTemplate.getDatabaseClient()
                .sql(sqlBuilder.toString())
                .map(row -> new MappingR2dbcConverter(new R2dbcMappingContext()).read(tClass, row))
                .all();
    }

    /**
     * 返回集合对象
     *
     * @param jdbcTemplate
     * @return reactor.core.publisher.Flux<java.util.Map<java.lang.String,java.lang.Object>>
     */
    public Flux<Map<String, Object>> list(R2dbcEntityTemplate jdbcTemplate) {

        printSql();
        return jdbcTemplate.getDatabaseClient()
                .sql(sqlBuilder.toString())
                .fetch()
                .all();
    }


    /**
     * 返回分页对象
     *
     * @param jdbcTemplate
     * @param pagingParam
     * @param tClass 返回值类型
     * @return reactor.core.publisher.Mono<org.springframework.data.domain.PageImpl<T>>
     */
    public <T> Mono<PageImpl<T>> page(R2dbcEntityTemplate jdbcTemplate, PagingParam pagingParam, Class<T> tClass) {

        if (pagingParam.getPageNumber() <= 0) {
            throw new RuntimeException("当前页数必须大于1");
        }
        if (pagingParam.getPageSize() <= 0) {
            throw new RuntimeException("每页大小必须大于1");
        }
        Pageable pageable = pagingParam.toPageable();

        String countSql = getSqlResultCount(this.sqlBuilder.toString());

        return jdbcTemplate.getDatabaseClient().sql(countSql)
                .map(row -> (Long) row.get(0))
                .first()
                .flatMap(totalSize -> {
                    if (totalSize == 0) {
                        return Mono.just(totalSize).map(size -> new PageImpl<T>(new ArrayList<>(), pageable, size));
                    }

                    int offset = (pagingParam.getPageNumber() - 1) * pagingParam.getPageSize();
                    int limit = pagingParam.getPageSize();
                    this.sqlBuilder.append(" limit ").append(limit).append(" offset ").append(offset).toString();
                    printSql();

                    Mono<PageImpl<T>> map = list(jdbcTemplate, tClass).collectList().map(content -> new PageImpl<>(content, pageable, totalSize));
                    return map;
                });


    }

    /**
     * 返回分页对象
     *
     * @param jdbcTemplate
     * @param pagingParam 分页参数
     * @return reactor.core.publisher.Mono<org.springframework.data.domain.PageImpl<java.util.Map<java.lang.String,java.lang.Object>>>
     */
    public Mono<PageImpl<Map<String, Object>>> page(R2dbcEntityTemplate jdbcTemplate, PagingParam pagingParam) {

        if (pagingParam.getPageNumber() <= 0) {
            throw new RuntimeException("当前页数必须大于1");
        }
        if (pagingParam.getPageSize() <= 0) {
            throw new RuntimeException("每页大小必须大于1");
        }
        Pageable pageable = pagingParam.toPageable();

        String countSql = getSqlResultCount(this.sqlBuilder.toString());

        return jdbcTemplate.getDatabaseClient().sql(countSql)
                .map(row -> (Long) row.get(0))
                .first()
                .flatMap(totalSize -> {
                    if (totalSize == 0) {
                        return Mono.just(totalSize).map(size -> new PageImpl<Map<String, Object>>(new ArrayList<>(), pageable, size));
                    }

                    int offset = (pagingParam.getPageNumber() - 1) * pagingParam.getPageSize();
                    int limit = pagingParam.getPageSize();
                    this.sqlBuilder.append(" limit ").append(limit).append(" offset ").append(offset).toString();
                    printSql();

                    Mono<PageImpl<Map<String, Object>>> map = list(jdbcTemplate).collectList().map(content -> new PageImpl<>(content, pageable, totalSize));
                    return map;
                });
    }

    /**
     * 获取统计总数的sql
     *
     * @param sql
     * @return java.lang.Integer
     */
    private String getSqlResultCount(String sql) {

        List<String> strList = Arrays.stream(sql.split(" "))
                .map(String::trim)
                .filter(StrUtil::isNotBlank)
                .toList();

        Stack<Integer> records = new Stack<>();
        records.push(0);
        long endIndex = 0;
        for (int i = 1; i < strList.size(); i++) {

            String str = strList.get(i);
            if ("from".equalsIgnoreCase(str)) {
                records.pop();
                if (records.empty()) {
                    endIndex = i;
                    break;
                }
            }

            if ("select".equalsIgnoreCase(str)) {
                records.push(i);
            }
        }


        String result = strList.stream()
                .skip(endIndex)
                .collect(Collectors.joining(" "));

        result = "SELECT COUNT(1) sum " + result;
        return result;
    }


    /**
     * 动态追加查询条件
     *
     * @param condition 条件
     * @param value     条件值
     * @return com.ss.contains.light.util.JdbcUtil
     */
    public JdbcUtil dynamicAppendCondition(String condition, String value) {

        if (StrUtil.isNotBlank(value)) {
            SqlSafeUtil.check(value);
            this.sqlBuilder.append(condition).append(" '").append(value).append("' ");
        }
        return this;
    }

    /**
     * 动态追加查询条件
     *
     * @param condition 条件
     * @param value     条件值
     * @return com.ss.contains.light.util.JdbcUtil
     */
    public JdbcUtil dynamicAppendCondition(String condition, Number value) {

        if (ObjectUtil.isNotNull(value)) {
            this.sqlBuilder.append(condition).append(value).append(" ");
        }
        return this;
    }

    /**
     * 动态追加查询条件
     *
     * @param condition 条件
     * @return com.ss.contains.light.util.JdbcUtil
     */
    public JdbcUtil dynamicAppendCondition(String condition) {

        if (StrUtil.isNotBlank(condition)) {
            SqlSafeUtil.check(condition);
            this.sqlBuilder.append(condition).append(" ");
        }
        return this;
    }

    /**
     * 动态追加查询条件
     *
     * @param condition
     * @param rList
     * @return com.ss.contains.light.util.JdbcUtil
     */
    public JdbcUtil dynamicAppendStrIn(String condition, List<String> rList) {

        if (ObjectUtil.isNotEmpty(rList)) {
            String s1 = rList.stream().map(s -> " '" + s + "'").collect(Collectors.joining(","));
            this.sqlBuilder.append(condition).append(" ( ").append(s1).append(" ) ");
        }
        return this;
    }

    /**
     * 动态追加查询条件
     *
     * @param condition
     * @param rList
     * @return com.ss.contains.light.util.JdbcUtil
     */
    public JdbcUtil dynamicAppendNumIn(String condition, List<Number> rList) {

        if (ObjectUtil.isNotEmpty(rList)) {
            String s1 = rList.stream().map(String::valueOf).collect(Collectors.joining(","));
            this.sqlBuilder.append(condition).append(" ( ").append(s1).append(" ) ");
        }
        return this;
    }

    /**
     * 追加倒叙排序条件
     *
     * @param field 排序字段
     * @return com.ss.contains.light.util.JdbcUtil
     */
    public JdbcUtil appendOrderByDesc(String field) {

        if (StrUtil.isNotBlank(field)) {
            SqlSafeUtil.check(field);
            this.sqlBuilder.append(" ORDER BY ").append(field).append(" DESC ");
        }
        return this;
    }

    /**
     * 追加升序排序条件
     *
     * @param field 排序字段
     * @return com.ss.contains.light.util.JdbcUtil
     */
    public JdbcUtil appendOrderByAsc(String field) {

        if (StrUtil.isNotBlank(field)) {
            SqlSafeUtil.check(field);
            this.sqlBuilder.append(" ORDER BY ").append(field).append(" ASC ");
        }

        return this;
    }

    /**
     * 打印sql
     */
    private void printSql() {

        try {
            log.info("待执行sql: {}", this.sqlBuilder.toString());
        } catch (Exception exception) {}

    }
}
