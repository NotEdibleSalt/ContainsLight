package com.ss.contains.light.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.ss.contains.light.base.DOBase;
import com.ss.contains.light.common.enums.AvailableStatusEnum;
import com.ss.contains.light.common.exception.Ex;
import com.ss.contains.light.common.paging.PagingResult;
import com.ss.contains.light.common.paging.TemplateFunction;
import com.ss.contains.light.controller.dto.command.RoleAssociatedAuthoitysSave;
import com.ss.contains.light.controller.dto.command.RoleAssociatedMenusSave;
import com.ss.contains.light.controller.dto.command.SaveRoleDTO;
import com.ss.contains.light.controller.dto.query.RolesPagingQuery;
import com.ss.contains.light.dos.RoleAccountDO;
import com.ss.contains.light.dos.RoleAuthorityDO;
import com.ss.contains.light.dos.RoleDO;
import com.ss.contains.light.dos.RoleMenuDO;
import com.ss.contains.light.repository.RoleAccountRepo;
import com.ss.contains.light.repository.RoleRepo;
import com.ss.contains.light.service.AuthorityService;
import com.ss.contains.light.service.RoleAuthorityService;
import com.ss.contains.light.service.RoleMenuService;
import com.ss.contains.light.service.RoleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @version 1.0
 */
@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleAccountRepo roleAccountRepo;
    private final RoleRepo roleRepo;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final RoleAuthorityService roleAuthorityService;
    private final AuthorityService authorityService;
    private final RoleMenuService roleMenuService;


    @Override
    public Flux<RoleDO> getAllRole() {
        return roleRepo.findAll();
    }

    @Override
    public Mono<PagingResult<RoleDO>> rolePaging(RolesPagingQuery rolesPagingQuery) {

        List<Criteria> criteriaList = TemplateFunction.baseQuery();
        if (StrUtil.isNotBlank(rolesPagingQuery.getName())) {
            Criteria name = Criteria.where("name").like(rolesPagingQuery.getName() + "%");
            criteriaList.add(name);
        }

        Query query = Query.query(Criteria.from(criteriaList));

        TemplateFunction<RoleDO> templateFun = () -> r2dbcEntityTemplate.select(RoleDO.class).from("role");
        return PagingResult.templatePaging(templateFun).param(rolesPagingQuery.toPageable(), query);
    }

    @Override
    public Mono<RoleDO> getRoleById(String id) {

        return roleRepo.findById(id);
    }

    @Override
    public Mono<RoleDO> getRoleByName(String name) {
        return roleRepo.findByNameAndDelFalse(name);
    }

    @Override
    public Mono<RoleDO> addRole(SaveRoleDTO saveRoleDTO) {

        RoleDO roleDO = saveRoleDTO.to();
        return roleRepo.save(roleDO);
    }

    @Override
    public Mono<RoleDO> updateRole(String id, SaveRoleDTO saveRoleDTO) {

        return roleRepo.findById(id)
                .map(roleDO -> {
                    roleDO.setName(saveRoleDTO.getName());
                    roleDO.setDescription(saveRoleDTO.getDescription());
                    return roleDO;
                })
                .flatMap(roleRepo::save);
    }

    @Override
    public Mono<RoleDO> delRoleById(String id) {
        return roleRepo.logicDelete(id);
    }

    @Override
    public Mono<RoleDO> enableRole(String id) {

        return roleRepo.findById(id)
                .doOnNext(roleDO -> {
                    if (ObjectUtil.isNull(roleDO) || roleDO.isDel()) {
                        throw new Ex("该角色不存在");
                    }
                    if (roleDO.getStatus().equals(AvailableStatusEnum.ENABLE)) {
                        throw new Ex("该角色已在启用状态");
                    }
                })
                .flatMap(roleDO -> {
                    roleDO.enabled();
                    Mono<RoleDO> save = roleRepo.save(roleDO);
                    return save;
                });
    }

    @Override
    public Mono<RoleDO> disableRole(String id) {
        return roleRepo.findById(id)
                .doOnNext(roleDO -> {
                    if (ObjectUtil.isNull(roleDO) || roleDO.isDel()) {
                        throw new Ex("该角色不存在");
                    }
                    if (roleDO.getStatus().equals(AvailableStatusEnum.DISABLE)) {
                        throw new Ex("该角色已在禁用状态");
                    }
                })
                .flatMap(roleDO -> {
                    roleDO.disabled();
                    Mono<RoleDO> save = roleRepo.save(roleDO);
                    return save;
                });
    }


    @Override
    public Mono<Void> saveRoleAssociatedAuthoitys(String roleId, RoleAssociatedAuthoitysSave roleAuthorityDOSet) {

        return roleRepo.findById(roleId)
                .doOnNext(roleDO -> {
                    if (ObjectUtil.isNull(roleDO) || roleDO.isDel()) {
                        throw new Ex("该角色不存在");
                    }
                })
                .map(DOBase::getId)
                .flatMapMany(roleAuthorityService::delAllRoleAssociatedAuthoitys)
                .thenMany(Flux.fromIterable(roleAuthorityDOSet.toRoleAuthorityDOSet(roleId)).flatMap(roleAuthorityService::saveRoleAssociatedAuthoity))
                .then();
    }

    @Override
    public Flux<RoleAccountDO> getAccountAllRole(String accountId) {

        return roleAccountRepo.findByAccountId(accountId).filter(RoleAccountDO::notDel);
    }

    @Override
    public Mono<RoleAccountDO> saveRoleAccount(String accountId, String roleId) {

        RoleAccountDO roleAccountDO = new RoleAccountDO();
        roleAccountDO.setAccountId(accountId);
        roleAccountDO.setRoleId(roleId);

        return roleAccountRepo.save(roleAccountDO);
    }

    @Override
    public Flux<RoleAccountDO> delAccountAllRole(String accountId) {

        return roleAccountRepo.findByAccountId(accountId)
                .map(DOBase::getId)
                .flatMap(roleAccountRepo::logicDelete);
    }

    @Override
    public Flux<String> getRoleAssociatedAuthoityIds(String roleId) {

        return roleAuthorityService.getRoleAssociatedAuthoitys(roleId)
                .map(RoleAuthorityDO::getAuthorityId)
                .distinct();
    }


    @Override
    public Mono<Void> saveRoleAssociatedMenus(String roleId, RoleAssociatedMenusSave roleAssociatedMenusSave) {

        return roleRepo.findById(roleId)
                .doOnNext(roleDO -> {
                    if (ObjectUtil.isNull(roleDO) || roleDO.isDel()) {
                        throw new Ex("该角色不存在");
                    }
                })
                .map(DOBase::getId)
                .flatMapMany(roleMenuService::delAllRoleAssociatedMenus)
                .thenMany(Flux.fromIterable(roleAssociatedMenusSave.toRoleMenuDOSet(roleId))
                                  .flatMap(roleMenuService::saveRoleAssociatedMenu)
                         )
                .then();
    }

    @Override
    public Flux<String> getRoleAssociatedMenuIds(String roleId) {

        return roleMenuService.getRoleAssociatedMenus(roleId)
                .map(RoleMenuDO::getMenuId)
                .distinct();
    }
}
