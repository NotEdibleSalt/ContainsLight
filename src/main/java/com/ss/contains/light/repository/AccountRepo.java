package com.ss.contains.light.repository;

import com.ss.contains.light.base.RepoBase;
import com.ss.contains.light.dos.AccountDO;
import reactor.core.publisher.Mono;

/**
 * 账户Repo
 *
 * @author NotEdibleSalt
 */
public interface AccountRepo extends RepoBase<AccountDO> {

    Mono<AccountDO> findByUsername(String username);
}


