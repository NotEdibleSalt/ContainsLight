package com.ss.contains.light.config.security;

import com.ss.contains.light.common.exception.Ex;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;

/**
 * 密码编码器工具
 *
 * @version 1.0
 * @date 2022-5-1
 */
@Slf4j
public class AuthUtil {

    /**
     * 获取密码编码器
     *
     * @return org.springframework.security.crypto.password.PasswordEncoder
     * @date 2022-5-1
     **/
    public static PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 获取登陆人用户名
     *
     * @return java.lang.String
     * @date 2022-5-2
     */
    public static Mono<String> getLoginUserName() {

        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .doOnNext(contest -> {
                    if (contest == null) {
                        throw new Ex("用户未登录");
                    }
                })
                .map(Authentication::getPrincipal)
                .doOnNext(contest -> {
                    if (contest == null) {
                        throw new Ex("用户未登录");
                    }
                })
                .cast(AdminUserDetails.class)
                .log()
                .map(AdminUserDetails::getUsername);
    }

    /**
     * 获取登陆人用户名
     * 未登录时返回null
     *
     * @return java.lang.String
     * @date 2022-5-2
     */
    public static Mono<String> getLoginUserNameNullAble() {

        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .cast(AdminUserDetails.class)
                .map(AdminUserDetails::getUsername);
    }

    /**
     * 获取登陆人用户名
     *
     * @return java.lang.String
     * @date 2022-5-2
     */
    public static Mono<AdminUserDetails> getLoginUserInfo() {

        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .doOnNext(contest -> {
                    if (contest == null) {
                        throw new Ex("用户未登录");
                    }
                })
                .map(Authentication::getPrincipal)
                .doOnNext(principal -> {
                    if (principal == null) {
                        throw new Ex("用户未登录");
                    }
                })
                .cast(AdminUserDetails.class);
    }

}
