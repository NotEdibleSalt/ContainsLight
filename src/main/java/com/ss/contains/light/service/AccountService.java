package com.ss.contains.light.service;

import com.ss.contains.light.dos.AccountDO;
import reactor.core.publisher.Mono;

/**
 * 账号Service
 */
public interface AccountService {

    /**
     * 禁用账号
     *
     * @param id 账号id
     * @return
     */
    Mono<AccountDO> disableAccount(String id);

    /**
     * 启用账号
     *
     * @param id 账号id
     * @return void
     */
    Mono<AccountDO> enableAccount(String id);
}
