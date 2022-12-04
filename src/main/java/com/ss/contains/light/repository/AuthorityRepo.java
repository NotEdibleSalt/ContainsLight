package com.ss.contains.light.repository;

import com.ss.contains.light.base.RepoBase;
import com.ss.contains.light.dos.AuthorityDO;
import org.springframework.data.r2dbc.repository.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 系统权限Repo
 *
 * @author NotEdibleSalt
 */
public interface AuthorityRepo extends RepoBase<AuthorityDO> {

    /**
     * @param adminId
     * @return reactor.core.publisher.Flux<com.ss.contains.light.dos.AuthorityDO>
     */
    @Query("SELECT " +
           "  au.*  " +
           "FROM " +
           "  authority au " +
           "  LEFT JOIN role_authority rau ON rau.authority_id = au.id  " +
           "  AND rau.del = FALSE" +
           "  LEFT JOIN role_account rac ON rac.role_id = rau.role_id  " +
           "  AND rac.del = FALSE  " +
           "WHERE " +
           "  au.del = FALSE  " +
           "  AND rac.account_id = :adminId")
    Flux<AuthorityDO> getByAdminId(String adminId);

    /**
     * 通过名称查询权限
     *
     * @param name 权限名称
     * @return reactor.core.publisher.Mono<com.ss.contains.light.dos.AuthorityDO>
     */
    Mono<AuthorityDO> findByNameAndDelFalse(String name);

    /**
     * 通过接口路径查询权限
     *
     * @param url 接口路径
     * @return reactor.core.publisher.Flux<com.ss.contains.light.dos.AuthorityDO>
     */
    Flux<AuthorityDO> findByUrlAndDelFalse(String url);

    /**
     * 查询所有未删除的权限
     *
     * @return reactor.core.publisher.Flux<com.ss.contains.light.dos.AuthorityDO>
     */
    Flux<AuthorityDO> findByDelFalse();
}


