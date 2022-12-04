package com.ss.contains.light.service;

import com.ss.contains.light.common.paging.PagingResult;
import com.ss.contains.light.controller.dto.command.RoleAssociatedAuthoitysSave;
import com.ss.contains.light.controller.dto.command.RoleAssociatedMenusSave;
import com.ss.contains.light.controller.dto.command.SaveRoleDTO;
import com.ss.contains.light.controller.dto.query.RolesPagingQuery;
import com.ss.contains.light.dos.RoleAccountDO;
import com.ss.contains.light.dos.RoleDO;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
 * 角色管理Service
 */
@Validated
public interface RoleService {


    /**
     * 分页查询角色
     *
     * @param rolesPagingQuery 分页查询角色参数
     * @return reactor.core.publisher.Mono<com.ss.contains.light.common.paging.PagingResult < com.ss.contains.light.dos.RoleDO>>
     */
    Mono<PagingResult<RoleDO>> rolePaging(RolesPagingQuery rolesPagingQuery);

    /**
     * 通过id查询角色
     *
     * @param id 角色id
     * @return reactor.core.publisher.Mono<com.ss.contains.light.dos.RoleDO>
     */
    Mono<RoleDO> getRoleById(String id);

    /**
     * 通过角色名查询角色
     *
     * @param name 角色名
     * @return reactor.core.publisher.Mono<com.ss.contains.light.dos.RoleDO>
     */
    Mono<RoleDO> getRoleByName(String name);

    /**
     * 新增角色
     *
     * @param saveRoleDTO
     * @return reactor.core.publisher.Mono<com.ss.contains.light.dos.RoleDO>
     */
    Mono<RoleDO> addRole(@Valid SaveRoleDTO saveRoleDTO);

    /**
     * 修改角色
     *
     * @param id          角色id
     * @param saveRoleDTO
     * @return reactor.core.publisher.Mono<com.ss.contains.light.dos.RoleDO>
     */
    Mono<RoleDO> updateRole(String id, @Valid SaveRoleDTO saveRoleDTO);

    /**
     * 删除角色
     *
     * @param id 角色id
     * @return reactor.core.publisher.Mono<com.ss.contains.light.dos.RoleDO>
     */
    Mono<RoleDO> delRoleById(String id);

    /**
     * 启用角色
     *
     * @param id 角色id
     * @return reactor.core.publisher.Mono<com.ss.contains.light.dos.RoleDO>
     */
    Mono<RoleDO> enableRole(String id);

    /**
     * 禁用角色
     *
     * @param id 角色id
     * @return reactor.core.publisher.Mono<com.ss.contains.light.dos.RoleDO>
     */
    Mono<RoleDO> disableRole(String id);

    /**
     * 保存角色关联的权限
     *
     * @param roleId                      角色id
     * @param roleAssociatedAuthoitysSave
     * @return reactor.core.publisher.Mono<java.lang.Void>
     */
    Mono<Void> saveRoleAssociatedAuthoitys(String roleId, RoleAssociatedAuthoitysSave roleAssociatedAuthoitysSave);

    /**
     * 获取全部角色
     *
     * @return reactor.core.publisher.Flux<com.ss.contains.light.dos.RoleDO>
     */
    Flux<RoleDO> getAllRole();

    /**
     * 获取账号所有权限
     *
     * @param accountId
     * @return reactor.core.publisher.Flux<com.ss.contains.light.dos.RoleAccountDO>
     */
    Flux<RoleAccountDO> getAccountAllRole(String accountId);

    /**
     * 保存角色权限关联
     *
     * @param accountId 账号id
     * @param roleId    角色id
     * @return reactor.core.publisher.Mono<com.ss.contains.light.dos.RoleAccountDO>
     */
    Mono<RoleAccountDO> saveRoleAccount(String accountId, String roleId);

    /**
     * 删除账号所有权限
     *
     * @param accountId
     * @return reactor.core.publisher.Flux<com.ss.contains.light.dos.RoleAccountDO>
     */
    Flux<RoleAccountDO> delAccountAllRole(String accountId);

    /**
     * 获取角色关联的权限id集合
     *
     * @param roleId 角色id
     * @return reactor.core.publisher.Flux<java.lang.String>
     */
    Flux<String> getRoleAssociatedAuthoityIds(String roleId);

    /**
     * 保存角色关联的菜单
     *
     * @param roleId    角色id
     * @param roleAssociatedMenusSave
     * @return reactor.core.publisher.Mono<java.lang.Void>
     */
    Mono<Void> saveRoleAssociatedMenus(String roleId, RoleAssociatedMenusSave roleAssociatedMenusSave);

    /**
     * 获取角色关联的菜单id集合
     *
     * @param roleId 角色id
     * @return reactor.core.publisher.Flux<java.lang.String>
     */
    Flux<String> getRoleAssociatedMenuIds(String roleId);
}
