package com.ss.contains.light.config.service.impl;

import com.ss.contains.light.config.security.AdminUserDetails;
import com.ss.contains.light.config.service.LoginService;
import com.ss.contains.light.controller.dto.command.LoginDTO;
import com.ss.contains.light.service.LoginRecordService;
import com.ss.contains.light.util.JWTUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * @version 1.0
 * @date 2022-5-2
 */
@Slf4j
@Service
@AllArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final ReactiveUserDetailsService reactiveUserDetailsService;
    private final LoginRecordService loginRecordService;

    @Override
    public Mono<String> token(LoginDTO loginDTO) {

        loginDTO.decrypt();

        return reactiveUserDetailsService.findByUsername(loginDTO.getUsername())
                .map(AdminUserDetails.class::cast)
                .doOnNext(userDetails -> userDetails.checkPassword(loginDTO.getPassword()))
                .map(JWTUtil::generateToken)
                .doOnNext(token -> loginRecordService.saveLoginRecord(token).subscribe());
    }
}
