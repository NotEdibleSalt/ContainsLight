package com.ss.contains.light.service.impl;

import cn.hutool.core.util.StrUtil;
import com.ss.contains.light.common.exception.Ex;
import com.ss.contains.light.common.paging.PagingResult;
import com.ss.contains.light.common.paging.TemplateFunction;
import com.ss.contains.light.controller.dto.query.LoginRecordPagingQuery;
import com.ss.contains.light.dos.LoginRecordDO;
import com.ss.contains.light.repository.LoginRecordRepo;
import com.ss.contains.light.service.AdminUserService;
import com.ss.contains.light.service.LoginRecordService;
import com.ss.contains.light.util.JWTUtil;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

/**
 * @author NotEdibleSalt
 */
@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class LoginRecordServiceImpl implements LoginRecordService {

    private final LoginRecordRepo loginRecordRepo;
    private final AdminUserService adminUserService;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    @Override
    public Mono<PagingResult<LoginRecordDO>> loginRecordPaging(LoginRecordPagingQuery loginRecordPagingQuery) {

        List<Criteria> criteriaList = TemplateFunction.baseQuery();
        if (StrUtil.isNotBlank(loginRecordPagingQuery.getLoginStatus())) {
            Criteria name = Criteria.where("login_status").is(loginRecordPagingQuery.getLoginStatus());
            criteriaList.add(name);
        }
        if (StrUtil.isNotBlank(loginRecordPagingQuery.getLoginAccount())) {
            Criteria type = Criteria.where("login_account").like(loginRecordPagingQuery.getLoginAccount() + "%");
            criteriaList.add(type);
        }
        if (StrUtil.isNotBlank(loginRecordPagingQuery.getLoginTime())) {

            String[] split = loginRecordPagingQuery.getLoginTime().split(",");
            Criteria type = Criteria.where("login_time").between(split[0], split[1]);
            criteriaList.add(type);
        }


        Query query = Query.query(Criteria.from(criteriaList));

        TemplateFunction<LoginRecordDO> templateFun = () -> r2dbcEntityTemplate.select(LoginRecordDO.class).from("login_record");
        return PagingResult.templatePaging(templateFun).param(loginRecordPagingQuery.toPageable(), query);
    }


    @Override
    public Mono<LoginRecordDO> saveLoginRecord(String token) {

        if (StrUtil.isBlank((token))) {
            throw new Ex("登录不能为空");
        }

        try {

            Claims claims = JWTUtil.getClaimsFromToken(token);
            String userName = claims.get("sub", String.class);

            return adminUserService.getAdminUserInfoByUserName(userName)
                    .map(adminUserPagingRO -> {

                        LoginRecordDO loginRecordDO = new LoginRecordDO();
                        loginRecordDO.setLoginUserId(adminUserPagingRO.getId());
                        loginRecordDO.setLoginName(adminUserPagingRO.getName());
                        loginRecordDO.setLoginAccount(adminUserPagingRO.getUsername());
                        loginRecordDO.setLoginTime(LocalDateTime.ofInstant(claims.getIssuedAt().toInstant(), ZoneId.systemDefault()));
                        loginRecordDO.setExpireDate(LocalDateTime.ofInstant(claims.getExpiration().toInstant(), ZoneId.systemDefault()));
                        loginRecordDO.setLoginStatus(LoginRecordDO.LoginStatus.ONLINE);
                        loginRecordDO.setToken(token);

                        return loginRecordDO;
                    })
                    .flatMap(loginRecordRepo::save);

        } catch (Exception exception) {

            throw new Ex("解析Token失败, 无法保存登录记录");
        }

    }
}
