package com.ss.contains.light.service;

import com.ss.contains.light.dos.RoleAuthorityDO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author NotEdibleSalt
 */
public interface RoleAuthorityService {

    /**
     * 获取角色关联的权限
     *
     * @param roleId 角色id
     * @return reactor.core.publisher.Flux<com.ss.contains.light.dos.RoleAuthorityDO>
     */
    Flux<RoleAuthorityDO> getRoleAssociatedAuthoitys(String roleId);

    /**
     * 保存角色关联的权限
     *
     * @param roleAuthorityDO
     * @return reactor.core.publisher.Mono<com.ss.contains.light.dos.RoleAuthorityDO>
     */
    Mono<RoleAuthorityDO> saveRoleAssociatedAuthoity(RoleAuthorityDO roleAuthorityDO);

    /**
     * 删除角色关联的权限
     *
     * @param roleId 角色id
     * @return reactor.core.publisher.Flux<com.ss.contains.light.dos.RoleAuthorityDO>
     */
    Flux<RoleAuthorityDO> delAllRoleAssociatedAuthoitys(String roleId);
}
