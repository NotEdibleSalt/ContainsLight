package com.ss.contains.light.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.ss.contains.light.base.DOBase;
import com.ss.contains.light.common.paging.PagingResult;
import com.ss.contains.light.config.security.AdminUserDetails;
import com.ss.contains.light.config.security.AuthUtil;
import com.ss.contains.light.controller.dto.command.AddAdminUserDTO;
import com.ss.contains.light.controller.dto.command.UpdateAdminUserDTO;
import com.ss.contains.light.controller.dto.query.AdminUserPagingQuery;
import com.ss.contains.light.controller.ro.AdminUserPagingRO;
import com.ss.contains.light.controller.ro.TreeDataRO;
import com.ss.contains.light.dos.*;
import com.ss.contains.light.repository.AccountRepo;
import com.ss.contains.light.repository.AdminUserRepo;
import com.ss.contains.light.repository.dynamic.AdminUserDao;
import com.ss.contains.light.service.AdminUserService;
import com.ss.contains.light.service.MenuService;
import com.ss.contains.light.service.RoleMenuService;
import com.ss.contains.light.service.RoleService;
import com.ss.contains.light.util.JdbcUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;


/**
 * @version 1.0
 * @date 2022-5-3
 */
@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class AdminUserServiceImpl implements AdminUserService {

    private final AdminUserRepo adminUserRepo;
    private final AdminUserDao adminUserDao;
    private final AccountRepo accountRepo;
    private final RoleService roleService;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final MenuService menuService;
    private final RoleMenuService roleMenuService;


    @Override
    public Mono<PagingResult<AdminUserPagingRO>> adminUserPaging(AdminUserPagingQuery adminUserPagingQuery) {

        Flux<AdminUserPagingRO> adminUserPagingROFlux = adminUserDao.adminUserPaging(adminUserPagingQuery);

        return PagingResult.physicalPaging(adminUserPagingQuery.toPageable(), adminUserPagingROFlux);
    }

    @Override
    public Mono<AdminUserDO> getAdminUser(String id) {

        return adminUserRepo.findById(id)
                .flatMap(adminUserDO -> roleService.getAccountAllRole(adminUserDO.getAccountId())
                                 .map(RoleAccountDO::getRoleId)
                                 .flatMap(roleService::getRoleById)
                                 .map(RoleDO::getName)
                                 .collect(Collectors.toSet())
                                 .map(roleNames -> {
                                     adminUserDO.setRoles(roleNames);
                                     return adminUserDO;
                                 })
                        );
    }

    @Override
    public Mono<AdminUserDO> addAdminUser(AddAdminUserDTO addAdminUserDTO) {

        log.info("新增管理员用户, 姓名: {} 用户名: {}", addAdminUserDTO.getName(), addAdminUserDTO.getUsername());

        addAdminUserDTO.validated();
        // TODO 手机号邮箱号验重

        AccountDO accountDO = addAdminUserDTO.toAccountDO();
        String randomNumbers = RandomUtil.randomNumbers(6);
        accountDO.setRandom(randomNumbers);
        String encode = AuthUtil.getPasswordEncoder().encode(accountDO.getPassword() + randomNumbers);
        accountDO.setPassword(encode);
        accountDO.disabled();

        return accountRepo.save(accountDO)
                .flatMap(accountDO1 -> {
                    AdminUserDO adminUserDO = addAdminUserDTO.toAdminUserDO();
                    adminUserDO.setAccountId(accountDO1.getId());
                    adminUserDO.setRoles(addAdminUserDTO.getRoles());
                    return adminUserRepo.save(adminUserDO);
                })
                .flatMap(adminUserDO -> Flux.fromIterable(addAdminUserDTO.getRoles())
                                 .flatMap(roleService::getRoleByName)
                                 .flatMap(roleDO -> roleService.saveRoleAccount(adminUserDO.getAccountId(), roleDO.getId()))
                                 .then()
                                 .thenReturn(adminUserDO)
                        );
    }

    @Override
    public Mono<AdminUserDO> updateAdminUser(String id, UpdateAdminUserDTO updateAdminUserDTO) {

        return adminUserRepo.findById(id)
                .map(adminUserDO -> {
                    adminUserDO.setEmail(updateAdminUserDTO.getEmail());
                    adminUserDO.setPhone(updateAdminUserDTO.getPhone());
                    adminUserDO.setName(updateAdminUserDTO.getName());
                    adminUserDO.setNickname(updateAdminUserDTO.getNickname());
                    adminUserDO.setAvatar(updateAdminUserDTO.getAvatar());
                    adminUserDO.setGender(updateAdminUserDTO.getGender());
                    adminUserDO.setRoles(updateAdminUserDTO.getRoles());
                    return adminUserDO;
                })
                .flatMap(adminUserRepo::save)
                .flatMap(adminUserDO -> roleService.delAccountAllRole(adminUserDO.getAccountId()).then().thenReturn(adminUserDO))
                .flatMap(adminUserDO -> Flux.fromIterable(updateAdminUserDTO.getRoles())
                                 .flatMap(roleService::getRoleByName)
                                 .flatMap(roleDO -> roleService.saveRoleAccount(adminUserDO.getAccountId(), roleDO.getId()))
                                 .then()
                                 .thenReturn(adminUserDO)
                        );
    }

    @Override
    public Mono<AccountDO> delAdminUser(String id) {

        return adminUserRepo.findById(id)
                .flatMap(adminUserRepo::logicDelete)
                .flatMap(adminUserDO -> {
                    String accountId = adminUserDO.getAccountId();
                    return accountRepo.findById(accountId).flatMap(accountRepo::logicDelete);
                })
                .doOnNext(accountDO -> roleService.delAccountAllRole(accountDO.getId()));
    }

    @Override
    public Mono<AdminUserPagingRO> getAdminUserInfoByUserName(String userName){

        String sql = "SELECT  " +
                     " au.id,  " +
                     " au.account_id,  " +
                     " au.email,  " +
                     " au.phone,  " +
                     " au.NAME,  " +
                     " au.nickname,  " +
                     " au.avatar,  " +
                     " au.gender,  " +
                     " ac.username,   " +
                     " ac.status   " +
                     "FROM  " +
                     " admin_user au  " +
                     " LEFT JOIN account ac ON au.account_id = ac.id   " +
                     " AND ac.del = FALSE   " +
                     "WHERE  " +
                     " au.del = FALSE ";

        return JdbcUtil.of(sql)
                .dynamicAppendCondition("AND ac.username = ", userName)
                .singleForObject(r2dbcEntityTemplate, AdminUserPagingRO.class);
    }

    @Override
    public Flux<MenuDO> getUserAllMenu(AdminUserDetails adminUserDetails) {

        if (adminUserDetails.isSuperAdmin()){
            return menuService.getAllMenus();
        }

        return roleService.getAccountAllRole(adminUserDetails.getId())
                .map(RoleAccountDO::getRoleId)
                .flatMap(roleMenuService::getRoleAssociatedMenus)
                .map(RoleMenuDO::getMenuId)
                .flatMap(menuService::getMenuById);

    }

    @Override
    public Mono<List<TreeDataRO>> getAdminMenuTree(AdminUserDetails adminUserDetails) {

        return getUserAllMenu(adminUserDetails)
                .collectList()
                .map(TreeDataRO::from);
    }
}
