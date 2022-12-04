package com.ss.contains.light.service;

import com.ss.contains.light.dos.RoleAuthorityDO;
import com.ss.contains.light.dos.RoleMenuDO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author NotEdibleSalt
 */
public interface RoleMenuService {

    /**
     * 获取角色关联的菜单
     *
     * @param roleId 角色id
     * @return reactor.core.publisher.Flux<com.ss.contains.light.dos.RoleMenuDO>
     */
    Flux<RoleMenuDO> getRoleAssociatedMenus(String roleId);

    /**
     * 保存角色关联的菜单
     *
     * @param roleMenuDO
     * @return reactor.core.publisher.Mono<com.ss.contains.light.dos.RoleMenuDO>
     */
    Mono<RoleMenuDO> saveRoleAssociatedMenu(RoleMenuDO roleMenuDO);

    /**
     * 删除角色关联的菜单
     *
     * @param roleId 角色id
     * @return reactor.core.publisher.Flux<com.ss.contains.light.dos.RoleMenuDO>
     */
    Flux<RoleMenuDO> delAllRoleAssociatedMenus(String roleId);
}
