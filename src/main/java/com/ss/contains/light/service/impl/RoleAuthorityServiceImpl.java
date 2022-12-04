package com.ss.contains.light.service.impl;

import com.ss.contains.light.dos.RoleAuthorityDO;
import com.ss.contains.light.repository.RoleAuthorityRepo;
import com.ss.contains.light.service.RoleAuthorityService;
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
public class RoleAuthorityServiceImpl implements RoleAuthorityService {

    private final RoleAuthorityRepo roleAuthorityRepo;

    @Override
    public Flux<RoleAuthorityDO> getRoleAssociatedAuthoitys(String roleId) {

        return roleAuthorityRepo.findByRoleIdAndDelFalse(roleId);
    }

    @Override
    public Mono<RoleAuthorityDO> saveRoleAssociatedAuthoity(RoleAuthorityDO roleAuthorityDO) {

        return roleAuthorityRepo.save(roleAuthorityDO);
    }

    @Override
    public Flux<RoleAuthorityDO> delAllRoleAssociatedAuthoitys(String roleId) {

        return roleAuthorityRepo.findByRoleIdAndDelFalse(roleId)
                .flatMap(roleAuthorityRepo::logicDelete);
    }
}
