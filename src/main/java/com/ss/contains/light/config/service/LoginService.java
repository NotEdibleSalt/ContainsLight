package com.ss.contains.light.config.service;

import com.ss.contains.light.controller.dto.command.LoginDTO;
import reactor.core.publisher.Mono;

/**
 * @author NotEdibleSalt
 * @version 1.0
 */
public interface LoginService {

    /**
     * 获取token
     *
     * @param loginDTO
     * @return reactor.core.publisher.Mono<java.lang.String>
     **/
    Mono<String> token(LoginDTO loginDTO);
}
