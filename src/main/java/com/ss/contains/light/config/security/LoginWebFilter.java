package com.ss.contains.light.config.security;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.ss.contains.light.common.AuthConstant;
import com.ss.contains.light.common.R;
import com.ss.contains.light.config.security.properties.IgnoreRequestsProperties;
import com.ss.contains.light.util.JWTUtil;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


/**
 * @version 1.0
 */
@Component
@AllArgsConstructor
public class LoginWebFilter implements WebFilter {

    private final IgnoreRequestsProperties ignoreRequestsProperties;
    private final ReactiveUserDetailsService reactiveUserDetailsService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        // 跨域的预检请求直接放行
        if (request.getMethod() == HttpMethod.OPTIONS) {
            return chain.filter(exchange);
        }

        URI uri = request.getURI();
        PathMatcher pathMatcher = new AntPathMatcher();
        //白名单路径直接放行
        boolean anyMatch = ignoreRequestsProperties.getRequestVOs()
                .stream()
                .anyMatch(requestVO -> pathMatcher.match(requestVO.getPath(), uri.getPath())
                                       && request.getMethod().matches(requestVO.getMethod().name()));
        if (anyMatch) {
            return chain.filter(exchange);
        }

        String token = request.getHeaders().getFirst(AuthConstant.JWT_TOKEN_HEADER);
        if (StrUtil.isBlank(token)) {
            R<Object> r = R.builder()
                    .code(402)
                    .message("未登录")
                    .build();
            return this.response(exchange, r);
        }
        Boolean expired = JWTUtil.isTokenExpired(token);
        if (expired) {
            R<Object> r = R.builder()
                    .code(401)
                    .message("登录过期")
                    .build();
            return this.response(exchange, r);
        }

        String adminUserName;
        try {
            Claims claims = JWTUtil.getClaimsFromToken(token);
            adminUserName = claims.get("sub", String.class);
        } catch (Exception exception) {
            R<Object> r = R.builder()
                    .code(406)
                    .message("无效Token")
                    .build();
            return this.response(exchange, r);
        }


        return reactiveUserDetailsService.findByUsername(adminUserName)
                .map(AdminUserDetails.class::cast)
                .map(adminUserDetails -> new UsernamePasswordAuthenticationToken(adminUserDetails, null, adminUserDetails.getAuthorities()))
                .flatMap(auth -> chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth)));
    }


    private Mono<Void> response(ServerWebExchange exchange, R<Object> r) {

        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.getHeaders().set("Access-Control-Allow-Origin", "*");
        response.getHeaders().set("Cache-Control", "no-cache");
        String body = JSONUtil.toJsonStr(r);
        DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
        Mono<Void> writeWith = response.writeWith(Mono.just(buffer));
        return writeWith;
    }

}


