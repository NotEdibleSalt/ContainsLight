package com.ss.contains.light.controller;

import com.ss.contains.light.common.paging.PagingResult;
import com.ss.contains.light.controller.dto.command.RoleAssociatedAuthoitysSave;
import com.ss.contains.light.controller.dto.command.RoleAssociatedMenusSave;
import com.ss.contains.light.controller.dto.command.SaveRoleDTO;
import com.ss.contains.light.controller.dto.query.RolesPagingQuery;
import com.ss.contains.light.dos.RoleDO;
import com.ss.contains.light.service.RoleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/**
 * @author NotEdibleSalt
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("roles")
public class RoleController {

    private final RoleService roleService;

    /**
     * 分页查询角色
     *
     * @param rolesPagingQuery 分页查询角色参数
     * @return reactor.core.publisher.Mono<com.ss.contains.light.common.paging.PagingResult < com.ss.contains.light.dos.RoleDO>>
     */
    @GetMapping()
    public Mono<PagingResult<RoleDO>> rolePaging(RolesPagingQuery rolesPagingQuery) {

        return roleService.rolePaging(rolesPagingQuery);
    }

    /**
     * 获取全部角色
     *
     * @return reactor.core.publisher.Flux<com.ss.contains.light.dos.RoleDO>
     */
    @GetMapping("list")
    public Flux<RoleDO> getAllRole() {

        return roleService.getAllRole();
    }


    /**
     * 通过id查询角色
     *
     * @param id 角色id
     * @return reactor.core.publisher.Mono<com.ss.contains.light.dos.RoleDO>
     */
    @GetMapping("{id}")
    public Mono<RoleDO> getRoleById(@PathVariable("id") String id) {

        return roleService.getRoleById(id);
    }

    /**
     * 新增角色
     *
     * @param saveRoleDTO
     * @return reactor.core.publisher.Mono<com.ss.contains.light.dos.RoleDO>
     */
    @PostMapping("")
    public Mono<RoleDO> addRole(@RequestBody SaveRoleDTO saveRoleDTO) {

        return roleService.addRole(saveRoleDTO);
    }


    /**
     * 修改角色
     *
     * @param id          角色id
     * @param saveRoleDTO
     * @return reactor.core.publisher.Mono<com.ss.contains.light.dos.RoleDO>
     */
    @PutMapping("{id}")
    public Mono<RoleDO> updateRole(@PathVariable("id") String id, @RequestBody SaveRoleDTO saveRoleDTO) {

        return roleService.updateRole(id, saveRoleDTO);
    }

    /**
     * 删除角色
     *
     * @param id 角色id
     * @return reactor.core.publisher.Mono<com.ss.contains.light.dos.RoleDO>
     */
    @DeleteMapping("{id}")
    public Mono<RoleDO> delRoleById(@PathVariable("id") String id) {

        return roleService.delRoleById(id);
    }

    /**
     * 启用角色
     *
     * @param id 角色id
     * @return reactor.core.publisher.Mono<com.ss.contains.light.dos.RoleDO>
     */
    @PutMapping("{id}/enable")
    public Mono<RoleDO> enableRole(@PathVariable("id") String id) {

        return roleService.enableRole(id);
    }

    /**
     * 禁用角色
     *
     * @param id 角色id
     * @return reactor.core.publisher.Mono<com.ss.contains.light.dos.RoleDO>
     */
    @PutMapping("{id}/disable")
    public Mono<RoleDO> disableRole(@PathVariable("id") String id) {

        return roleService.disableRole(id);
    }

    /**
     * 保存角色关联的菜单
     *
     * @param roleId    角色id
     * @param roleAssociatedMenusSave
     * @return reactor.core.publisher.Mono
     */
    @PostMapping("{roleId}/menus")
    public Mono saveRoleAssociatedMenus(@PathVariable("roleId") String roleId, @RequestBody RoleAssociatedMenusSave roleAssociatedMenusSave) {

        return roleService.saveRoleAssociatedMenus(roleId, roleAssociatedMenusSave).thenReturn("关联成功");
    }


    /**
     * 获取角色关联的菜单id集合
     *
     * @param roleId 角色id
     * @return reactor.core.publisher.Flux<java.lang.String>
     */
    @GetMapping("{roleId}/menus/list")
    public Flux<String> getRoleAssociatedMenuIds(@PathVariable("roleId") String roleId) {

        return roleService.getRoleAssociatedMenuIds(roleId);
    }

    /**
     * 保存角色关联的权限
     *
     * @param roleId                      角色id
     * @param roleAssociatedAuthoitysSave
     * @return reactor.core.publisher.Mono
     */
    @PostMapping("{roleId}/authoitys")
    public Mono saveRoleAssociatedAuthoitys(@PathVariable("roleId") String roleId, @RequestBody RoleAssociatedAuthoitysSave roleAssociatedAuthoitysSave) {

        return roleService.saveRoleAssociatedAuthoitys(roleId, roleAssociatedAuthoitysSave).thenReturn("关联成功");
    }


    /**
     * 获取角色关联的权限id集合
     *
     * @param roleId 角色id
     * @return reactor.core.publisher.Flux<java.lang.String>
     */
    @GetMapping("{roleId}/authoitys/list")
    public Flux<String> getRoleAssociatedAuthoityIds(@PathVariable("roleId") String roleId) {

        return roleService.getRoleAssociatedAuthoityIds(roleId);
    }
}
