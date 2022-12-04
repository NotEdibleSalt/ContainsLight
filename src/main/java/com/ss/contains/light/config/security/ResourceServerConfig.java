package com.ss.contains.light.config.security;

import cn.hutool.core.util.ArrayUtil;
import com.ss.contains.light.common.enums.MethodEnum;
import com.ss.contains.light.config.security.properties.IgnoreRequestsProperties;
import com.ss.contains.light.config.security.properties.RequestVO;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author NotEdibleSalt
 * @version 1.0
 */
@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@AllArgsConstructor
public class ResourceServerConfig {

    private final ReactiveAuthorizationManager reactiveAuthorizationManager;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    private final IgnoreRequestsProperties ignoreRequestsProperties;
    private final LoginWebFilter permissionWebFilter;


    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {

        Map<MethodEnum, List<RequestVO>> groupHttpMethodMap = ignoreRequestsProperties.groupHttpMethod();
        String[] getPaths = groupHttpMethodMap.getOrDefault(RequestMethod.GET, new ArrayList<>()).toArray(String[]::new);
        String[] postPaths = groupHttpMethodMap.getOrDefault(RequestMethod.POST, new ArrayList<>()).toArray(String[]::new);
        String[] putPaths = groupHttpMethodMap.getOrDefault(RequestMethod.PUT, new ArrayList<>()).toArray(String[]::new);
        String[] deletePaths = groupHttpMethodMap.getOrDefault(RequestMethod.DELETE, new ArrayList<>()).toArray(String[]::new);


        http.addFilterBefore(permissionWebFilter, SecurityWebFiltersOrder.AUTHORIZATION);
        ServerHttpSecurity.AuthorizeExchangeSpec authorizeExchangeSpec = http.authorizeExchange();

        if (ArrayUtil.isNotEmpty(getPaths)) {
            authorizeExchangeSpec.pathMatchers(HttpMethod.GET, getPaths).permitAll();
        }
        if (ArrayUtil.isNotEmpty(postPaths)) {
            authorizeExchangeSpec.pathMatchers(HttpMethod.POST, postPaths).permitAll();
        }
        if (ArrayUtil.isNotEmpty(putPaths)) {
            authorizeExchangeSpec.pathMatchers(HttpMethod.PUT, putPaths).permitAll();
        }
        if (ArrayUtil.isNotEmpty(deletePaths)) {
            authorizeExchangeSpec.pathMatchers(HttpMethod.DELETE, deletePaths).permitAll();
        }

        authorizeExchangeSpec.anyExchange().access(reactiveAuthorizationManager)
                .and().exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                .csrf().disable();

        return http.build();
    }

}
