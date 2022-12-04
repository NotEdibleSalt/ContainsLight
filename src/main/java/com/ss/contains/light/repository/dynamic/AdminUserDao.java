package com.ss.contains.light.repository.dynamic;

import com.ss.contains.light.controller.dto.query.AdminUserPagingQuery;
import com.ss.contains.light.controller.ro.AdminUserPagingRO;
import com.ss.contains.light.util.JdbcUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Slf4j
@Component
@AllArgsConstructor
public class AdminUserDao {

    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    public Flux<AdminUserPagingRO> adminUserPaging(AdminUserPagingQuery adminUserPagingQuery) {

        String sql = "SELECT  " +
                     "  au.id,  " +
                     "  au.email,  " +
                     "  au.phone,  " +
                     "  au.name,  " +
                     "  au.nickname,  " +
                     "  au.avatar,  " +
                     "  au.gender,  " +
                     "  au.account_id,  " +
                     "  a.username,  " +
                     "  a.status   " +
                     "FROM  " +
                     "  admin_user au  " +
                     "LEFT JOIN account a ON au.account_id = a.id   " +
                     "  WHERE  " +
                     "  au.del is FALSE";

        return JdbcUtil.of(sql)
                .dynamicAppendCondition(" and au.phone = ", adminUserPagingQuery.getPhone())
                .dynamicAppendCondition(" and au.phone = ", adminUserPagingQuery.getPhone())
                .dynamicAppendCondition(" and au.phone = ", adminUserPagingQuery.getPhone())
                .list(r2dbcEntityTemplate, AdminUserPagingRO.class);

    }

}
