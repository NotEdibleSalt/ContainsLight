package com.ss.contains.light.repository;

import com.ss.contains.light.base.RepoBase;
import com.ss.contains.light.dos.RoleAccountDO;
import reactor.core.publisher.Flux;

/**
 * 角色、账号关联表Repo
 *
 * @author NotEdibleSalt
 */
public interface RoleAccountRepo extends RepoBase<RoleAccountDO> {

    Flux<RoleAccountDO> findByAccountId(String accountId);
}


