package com.ss.contains.light.repository;

import com.ss.contains.light.base.RepoBase;
import com.ss.contains.light.dos.RoleMenuDO;
import reactor.core.publisher.Flux;

/**
 * 角色、菜单关联表Repo
 *
 * @author NotEdibleSalt
 */
public interface RoleMenuRepo extends RepoBase<RoleMenuDO> {

    Flux<RoleMenuDO> findByRoleIdAndDelFalse(String roleId);
}


