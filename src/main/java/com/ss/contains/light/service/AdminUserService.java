package com.ss.contains.light.service;

import com.ss.contains.light.common.paging.PagingResult;
import com.ss.contains.light.config.security.AdminUserDetails;
import com.ss.contains.light.controller.dto.command.AddAdminUserDTO;
import com.ss.contains.light.controller.dto.command.UpdateAdminUserDTO;
import com.ss.contains.light.controller.dto.query.AdminUserPagingQuery;
import com.ss.contains.light.controller.ro.AdminUserPagingRO;
import com.ss.contains.light.dos.AccountDO;
import com.ss.contains.light.dos.AdminUserDO;
import com.ss.contains.light.dos.MenuDO;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

public interface AdminUserService {


    /**
     * 条件查询管理员用户
     *
     * @param adminUserPagingQuery
     * @return reactor.core.publisher.Mono<com.ss.contains.light.common.paging.PagingResult < com.ss.contains.light.controller.ro.AdminUserPagingRO>>
     * @date 2022-5-2
     */
    Mono<PagingResult<AdminUserPagingRO>> adminUserPaging(AdminUserPagingQuery adminUserPagingQuery);

    /**
     * 查询管理员用户信息
     *
     * @param id 管理员用户id
     * @return reactor.core.publisher.Mono<com.ss.contains.light.dos.AdminUserDO>
     **/
    Mono<AdminUserDO> getAdminUser(String id);

    /**
     * 新增管理员用户
     *
     * @param addAdminUserDTO 新增管理员用户DTO
     * @return reactor.core.publisher.Mono<com.ss.contains.light.dos.AdminUserDO>
     **/
    Mono<AdminUserDO> addAdminUser(@Valid AddAdminUserDTO addAdminUserDTO);

    /**
     * 更新管理员用户
     *
     * @param id                 管理员用户id
     * @param updateAdminUserDTO 更新管理员用户信息DTO
     * @return reactor.core.publisher.Mono<com.ss.contains.light.dos.AdminUserDO>
     * @date 2022-5-2
     */
    Mono<AdminUserDO> updateAdminUser(String id, UpdateAdminUserDTO updateAdminUserDTO);

    /**
     * 删除管理员用户
     *
     * @param id 管理员用户id
     * @return com.ss.contains.light.common.R
     */
    Mono<AccountDO> delAdminUser(String id);

    /**
     * 通过登录账号查询用户信息
     *
     * @param userName 登录账号
     * @return reactor.core.publisher.Mono<com.ss.contains.light.controller.ro.AdminUserPagingRO>
     */
    Mono<AdminUserPagingRO> getAdminUserInfoByUserName(String userName);

    /**
     * 获取用户所有菜单
     *
     * @param adminUserDetails 登录用户信息
     * @return reactor.core.publisher.Flux<com.ss.contains.light.dos.MenuDO>
     */
    Flux<MenuDO> getUserAllMenu(AdminUserDetails adminUserDetails);
}
