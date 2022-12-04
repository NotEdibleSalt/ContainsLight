package com.ss.contains.light.repository;

import com.ss.contains.light.base.RepoBase;
import com.ss.contains.light.dos.RoleAuthorityDO;
import reactor.core.publisher.Flux;

/**
 * 角色、权限关联表Repo
 *
 * @author NotEdibleSalt
 */
public interface RoleAuthorityRepo extends RepoBase<RoleAuthorityDO> {

    Flux<RoleAuthorityDO> findByRoleIdAndDelFalse(String roleId);
}


