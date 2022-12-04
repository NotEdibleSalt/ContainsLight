package com.ss.contains.light.config.security;

import cn.hutool.core.util.ObjectUtil;
import com.ss.contains.light.config.security.properties.IgnoreRequestsProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Collection;

/**
 * @author NotEdibleSalt
 * @version 1.0
 */
@Slf4j
@Component
@AllArgsConstructor
public class ReactiveAuthorizationManagerImpl implements ReactiveAuthorizationManager<AuthorizationContext> {

    private final IgnoreRequestsProperties ignoreRequestsProperties;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {

        ServerHttpRequest request = authorizationContext.getExchange().getRequest();

        // 跨域的预检请求直接放行
        if (request.getMethod() == HttpMethod.OPTIONS) {
            return Mono.just(new AuthorizationDecision(true));
        }

        URI uri = request.getURI();
        PathMatcher pathMatcher = new AntPathMatcher();
        //白名单路径直接放行
        boolean anyMatch = ignoreRequestsProperties.getRequestVOs()
                .stream()
                .anyMatch(requestVO -> pathMatcher.match(requestVO.getPath(), uri.getPath())
                                       && request.getMethod().matches(requestVO.getMethod().name()));
        if (anyMatch) {
            return Mono.just(new AuthorizationDecision(true));
        }

        return AuthUtil.getLoginUserInfo()
                .map(adminUserDetails -> {

                    // 超管直接放行
                    if (adminUserDetails.isSuperAdmin()) {
                        return true;
                    }
                    Collection<? extends GrantedAuthority> authorities = adminUserDetails.getAuthorities();
                    if (ObjectUtil.isEmpty(authorities)) {
                        return false;
                    }

                    return authorities
                            .stream()
                            .anyMatch(authority -> {

                                String[] split = authority.getAuthority().split(":");
                                return pathMatcher.match(uri.getPath(), split[1])
                                       && request.getMethod().matches(split[0]);
                            });

                })
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
    }

}
