package com.ss.contains.light.controller;

import com.ss.contains.light.config.service.LoginService;
import com.ss.contains.light.controller.dto.command.LoginDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @version 1.0
 */
@Slf4j
@RestController
@AllArgsConstructor
public class LoginController {

    private final LoginService loginService;

    /**
     * 登录
     *
     * @param loginDTO 登录参数
     * @return reactor.core.publisher.Mono<java.lang.String>
     * @date 2022-5-2
     **/
    @PostMapping("login")
    public Mono<String> login(@RequestBody LoginDTO loginDTO) {

        Mono<String> token = loginService.token(loginDTO);
        return token;
    }


}
