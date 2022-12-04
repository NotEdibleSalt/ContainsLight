package com.ss.contains.light.service.impl;

import com.ss.contains.light.dos.AccountDO;
import com.ss.contains.light.repository.AccountRepo;
import com.ss.contains.light.service.AccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;


@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepo accountRepo;

    @Override
    public Mono<AccountDO> disableAccount(String id) {

        return accountRepo.findById(id)
                .flatMap(accountDO -> {
                    accountDO.disabled();
                    return accountRepo.save(accountDO);
                });
    }

    @Override
    public Mono<AccountDO> enableAccount(String id) {

        return accountRepo.findById(id)
                .flatMap(accountDO -> {
                    accountDO.enabled();
                    return accountRepo.save(accountDO);
                });
    }
}
