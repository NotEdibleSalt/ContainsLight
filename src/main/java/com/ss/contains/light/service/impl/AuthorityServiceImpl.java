package com.ss.contains.light.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.ss.contains.light.common.CacheConstant;
import com.ss.contains.light.common.enums.MethodEnum;
import com.ss.contains.light.common.exception.Ex;
import com.ss.contains.light.common.paging.PagingResult;
import com.ss.contains.light.common.paging.TemplateFunction;
import com.ss.contains.light.controller.dto.command.SaveAuthorityDTO;
import com.ss.contains.light.controller.dto.query.AuthoritysPagingQuery;
import com.ss.contains.light.controller.ro.TreeDataRO;
import com.ss.contains.light.dos.AuthorityDO;
import com.ss.contains.light.repository.AuthorityRepo;
import com.ss.contains.light.service.AuthorityService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author NotEdibleSalt
 */
@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepo authorityRepo;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final ReactiveRedisTemplate reactiveRedisTemplate;

    @Override
    public Mono<PagingResult<AuthorityDO>> authorityPaging(AuthoritysPagingQuery authoritysPagingQuery) {


        List<Criteria> criteriaList = TemplateFunction.baseQuery();
        if (StrUtil.isNotBlank(authoritysPagingQuery.getName())) {
            Criteria name = Criteria.where("name").like(authoritysPagingQuery.getName() + "%");
            criteriaList.add(name);
        }
        if (StrUtil.isNotBlank(authoritysPagingQuery.getModule())) {
            Criteria url = Criteria.where("module").like(authoritysPagingQuery.getModule() + "%");
            criteriaList.add(url);
        }
        if (ObjectUtil.isNotNull(authoritysPagingQuery.getMethod())) {
            Criteria method = Criteria.where("method").is(authoritysPagingQuery.getMethod());
            criteriaList.add(method);
        }

        Query query = Query.query(Criteria.from(criteriaList));

        TemplateFunction<AuthorityDO> templateFun = () -> r2dbcEntityTemplate.select(AuthorityDO.class).from("authority");
        return PagingResult.templatePaging(templateFun).param(authoritysPagingQuery.toPageable(), query);
    }

    @Override
    public Mono<AuthorityDO> authorityById(String id) {
        return authorityRepo.findById(id);
    }

    @Override
    public Mono<AuthorityDO> addAuthority(SaveAuthorityDTO saveAuthorityDTO) {

        return authorityRepo.findByNameAndDelFalse(saveAuthorityDTO.getName())
                .hasElement()
                .doOnNext(hasElement -> {
                    if (hasElement) {
                        throw new Ex("权限名称已存在");
                    }
                })
                .thenMany(authorityRepo.findByUrlAndDelFalse(saveAuthorityDTO.getUrl()))
                .doOnNext(authorityDO -> {
                    if (ObjectUtil.isNotNull(authorityDO) && authorityDO.getMethod().equals(saveAuthorityDTO.getMethod())) {
                        throw new Ex("接口已存在");
                    }
                })
                .then()
                .thenReturn(saveAuthorityDTO.to())
                .log()
                .flatMap(authorityRepo::save);
    }

    @Override
    public Mono<AuthorityDO> updateAuthority(String id, SaveAuthorityDTO saveAuthorityDTO) {

        return authorityRepo.findByNameAndDelFalse(saveAuthorityDTO.getName())
                .doOnNext(authorityDO -> {
                    if (ObjectUtil.isNotNull(authorityDO) && !authorityDO.getId().equals(id)) {
                        throw new Ex("权限名称已存在");
                    }
                })
                .thenMany(authorityRepo.findByUrlAndDelFalse(saveAuthorityDTO.getUrl()))
                .doOnNext(authorityDO -> {
                    if (ObjectUtil.isNotNull(authorityDO) && authorityDO.getMethod().equals(saveAuthorityDTO.getMethod()) && !authorityDO.getId().equals(id)) {
                        throw new Ex("接口已存在");
                    }
                })
                .then()
                .thenReturn(saveAuthorityDTO.to())
                .map(authorityDO -> {
                    authorityDO.setId(id);
                    return authorityDO;
                })
                .flatMap(authorityRepo::save);
    }

    @Override
    public Mono<AuthorityDO> delAuthority(String id) {

        return authorityRepo.logicDelete(id);
    }


    @Override
    public Mono<List<TreeDataRO>> getAuthorityTree() {

        Mono<List<TreeDataRO>> map = authorityRepo.findByDelFalse()
                .collectList()
                .map(authorityDOList ->
                             authorityDOList.stream().collect(Collectors.groupingBy(AuthorityDO::getModule))
                                     .entrySet()
                                     .stream()
                                     .map(entry -> {

                                         TreeDataRO treeDataRO = new TreeDataRO(entry.getKey() + ":", entry.getKey());

                                         List<TreeDataRO> mapList = entry.getValue()
                                                 .stream()
                                                 .collect(Collectors.groupingBy(AuthorityDO::getMethod))
                                                 .entrySet()
                                                 .stream()
                                                 .map(entry2 -> {

                                                     TreeDataRO treeDataRO1 = new TreeDataRO(entry.getKey() + ":" + entry2.getKey(),entry2.getKey().name());

                                                     if (entry2.getValue() != null && entry2.getValue().size() > 0) {

                                                         List<TreeDataRO> treeDataLeaf = entry2.getValue()
                                                                 .stream()
                                                                 .map(authorityDO -> {
                                                                     String title = authorityDO.getName() + " : " + authorityDO.getMethod() + " " + authorityDO.getUrl();
                                                                     return new TreeDataRO(authorityDO.getId(),title);
                                                                 })
                                                                 .collect(Collectors.toList());

                                                         treeDataRO1.setChildren(treeDataLeaf);
                                                     }
                                                     return treeDataRO1;
                                                 })
                                                 .collect(Collectors.toList());

                                         treeDataRO.setChildren(mapList);

                                         return treeDataRO;
                                     })
                                     .collect(Collectors.toList()));


        return map;

    }

    @Override
    public Flux<String> getAuthorityAllModule() {

        return authorityRepo.findByDelFalse()
                .map(AuthorityDO::getModule)
                .distinct();
    }




    @Override
    public Flux<String> getAdminAuthority(String adminId) {

        // 延迟执行的查询AuthorityDO操作
        Flux<String> deferAuthorityDOFlux = Flux.defer(
                () -> authorityRepo.getByAdminId(adminId)
                        .flatMap(authorityDO -> {
                            String authority = authorityDO.getMethod().name() + ":" + authorityDO.getUrl();
                            return reactiveRedisTemplate.opsForHash()
                                    .put(CacheConstant.ADMIN_AUTHORITY_PRE + adminId, authorityDO.getId(), authority)
                                    .thenReturn(authority);
                        })
                                                      );

        // 从redis中获取用户的权限
        Flux<String> authorityDOFlux = reactiveRedisTemplate.opsForHash().values(CacheConstant.ADMIN_AUTHORITY_PRE + adminId)
                .cast(String.class)
                .switchIfEmpty(deferAuthorityDOFlux);

        return authorityDOFlux;
    }
}
