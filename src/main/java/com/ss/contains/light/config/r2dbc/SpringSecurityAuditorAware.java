package com.ss.contains.light.config.r2dbc;

import com.ss.contains.light.config.security.AuthUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.ReactiveAuditorAware;
import reactor.core.publisher.Mono;

/**
 * r2dbc自动填充审计字段
 *
 * @author NotEdibleSalt
 * @version 1.0
 */
@Slf4j
public class SpringSecurityAuditorAware implements ReactiveAuditorAware<String> {

    @Override
    public Mono<String> getCurrentAuditor() {

        return AuthUtil.getLoginUserNameNullAble()
                .defaultIfEmpty("default");
    }
}
