package com.ss.contains.light.repository;

import com.ss.contains.light.base.RepoBase;
import com.ss.contains.light.dos.RoleDO;
import reactor.core.publisher.Mono;

/**
 * 角色表Repo
 *
 * @author NotEdibleSalt
 */
public interface RoleRepo extends RepoBase<RoleDO> {

    Mono<RoleDO> findByNameAndDelFalse(String name);
}


