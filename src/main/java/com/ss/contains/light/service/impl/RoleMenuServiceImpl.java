package com.ss.contains.light.service.impl;

import com.ss.contains.light.dos.RoleAuthorityDO;
import com.ss.contains.light.dos.RoleMenuDO;
import com.ss.contains.light.repository.RoleAuthorityRepo;
import com.ss.contains.light.repository.RoleMenuRepo;
import com.ss.contains.light.service.RoleMenuService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author NotEdibleSalt
 */
@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class RoleMenuServiceImpl implements RoleMenuService {

    private final RoleMenuRepo roleMenuRepo;

    @Override
    public Flux<RoleMenuDO> getRoleAssociatedMenus(String roleId) {

        return roleMenuRepo.findByRoleIdAndDelFalse(roleId);
    }

    @Override
    public Mono<RoleMenuDO> saveRoleAssociatedMenu(RoleMenuDO roleMenuDO) {

        return roleMenuRepo.save(roleMenuDO);
    }

    @Override
    public Flux<RoleMenuDO> delAllRoleAssociatedMenus(String roleId) {

        return roleMenuRepo.findByRoleIdAndDelFalse(roleId)
                .flatMap(roleMenuRepo::logicDelete);
    }
}
