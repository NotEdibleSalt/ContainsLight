package com.ss.contains.light.controller;

import com.ss.contains.light.common.MessageCommon;
import com.ss.contains.light.common.R;
import com.ss.contains.light.common.paging.PagingResult;
import com.ss.contains.light.config.security.AuthUtil;
import com.ss.contains.light.controller.dto.command.AddAdminUserDTO;
import com.ss.contains.light.controller.dto.command.UpdateAdminUserDTO;
import com.ss.contains.light.controller.dto.query.AdminUserPagingQuery;
import com.ss.contains.light.controller.ro.AdminUserPagingRO;
import com.ss.contains.light.controller.ro.TreeDataRO;
import com.ss.contains.light.dos.AdminUserDO;
import com.ss.contains.light.dos.MenuDO;
import com.ss.contains.light.service.AdminUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


@AllArgsConstructor
@RequestMapping("admins")
@RestController
public class AdminUserController {

    private final AdminUserService adminUserService;

    /**
     * 条件查询管理员用户
     *
     * @param adminUserPagingQuery 查询参数
     *
     * @return reactor.core.publisher.Mono<com.ss.contains.light.common.paging.PagingResult < com.ss.contains.light.dos.AdminUserDO>>
     * @date 2022-5-2
     */
    @GetMapping("")
    public Mono<PagingResult<AdminUserPagingRO>> adminUserPaging(AdminUserPagingQuery adminUserPagingQuery) {

        return adminUserService.adminUserPaging(adminUserPagingQuery);
    }

    /**
     * 查询管理员用户信息
     *
     * @param id 管理员用户id
     *
     * @return reactor.core.publisher.Mono<com.ss.contains.light.dos.AdminUserDO>
     **/
    @GetMapping("{id}")
    public Mono<AdminUserDO> getAdminUser(@PathVariable("id") String id) {

        return adminUserService.getAdminUser(id);
    }

    /**
     * 新增管理员用户
     *
     * @param addAdminUserDTO 新增管理员用户DTO
     *
     * @return reactor.core.publisher.Mono<com.ss.contains.light.dos.AdminUserDO>
     **/
    @PostMapping("")
    public Mono<AdminUserDO> addAdminUser(@RequestBody AddAdminUserDTO addAdminUserDTO) {

        return adminUserService.addAdminUser(addAdminUserDTO);
    }

    /**
     * 更新管理员用户
     *
     * @param id                 管理员用户id
     * @param updateAdminUserDTO 更新管理员用户信息DTO
     *
     * @return reactor.core.publisher.Mono<com.ss.contains.light.dos.AdminUserDO>
     * @date 2022-5-2
     */
    @PutMapping("/{id}")
    public Mono<AdminUserDO> updateAdminUser(@PathVariable("id") String id, @RequestBody UpdateAdminUserDTO updateAdminUserDTO) {

        return adminUserService.updateAdminUser(id, updateAdminUserDTO);
    }

    /**
     * 删除管理员用户
     *
     * @param id 管理员用户id
     *
     * @return com.ss.contains.light.common.R
     * @date 2022-5-2
     */
    @DeleteMapping("/{id}")
    public Mono<R<Object>> delAdminUser(@PathVariable("id") String id) {

        return adminUserService.delAdminUser(id).thenReturn(R.message(MessageCommon.DEL_SUCCESS));
    }

    /**
     * 获取用户所有菜单
     *
     * @return reactor.core.publisher.Flux<com.ss.contains.light.dos.MenuDO>
     */
    @GetMapping("menus")
    public Flux<MenuDO> getUserAllMenu() {

        return AuthUtil.getLoginUserInfo()
                .flatMapMany(adminUserService::getUserAllMenu);

    }

    /**
     * 查询用户的菜单树
     *
     * @return reactor.core.publisher.Mono<java.util.List < com.ss.contains.light.controller.ro.TreeDataRO>>
     */
    @GetMapping("menus/tree")
    public Mono<List<TreeDataRO>> getAdminMenuTree() {

        return AuthUtil.getLoginUserInfo()
                .flatMap(adminUserService::getAdminMenuTree);
    }


}
