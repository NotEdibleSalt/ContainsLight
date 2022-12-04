package com.ss.contains.light.controller;

import com.ss.contains.light.common.R;
import com.ss.contains.light.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


/**
 * 账号Controller
 */
@RestController
@RequestMapping("accounts")
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;

    /**
     * 禁用账号
     *
     * @param id 账号id
     * @return com.ss.contains.light.common.R<java.lang.Object>
     */
    @PutMapping("/{id}/disable")
    public Mono<R<Object>> disableAccount(@PathVariable("id") String id) {

        return accountService.disableAccount(id).thenReturn(R.message("已禁用该账号"));
    }

    /**
     * 启用账号
     *
     * @param id 账号id
     * @return com.ss.contains.light.common.R<java.lang.Object>
     */
    @PutMapping("/{id}/enable")
    public Mono<R<Object>> enableAccount(@PathVariable("id") String id) {

        return accountService.enableAccount(id).thenReturn(R.message("已启用该账号"));
    }


}
