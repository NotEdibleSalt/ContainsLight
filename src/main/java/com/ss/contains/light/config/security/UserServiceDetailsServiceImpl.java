package com.ss.contains.light.config.security;

import com.ss.contains.light.dos.AccountDO;
import com.ss.contains.light.repository.AccountRepo;
import com.ss.contains.light.service.AuthorityService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author NotEdibleSalt
 * @version 1.0
 */
@Component
@AllArgsConstructor
public class UserServiceDetailsServiceImpl implements ReactiveUserDetailsService {

    private final AccountRepo accountRepo;
    private final AuthorityService authorityService;

    @Override
    public Mono<UserDetails> findByUsername(String username) {

        Mono<AccountDO> accountDOMono = accountRepo.findByUsername(username);
        Mono<List<SimpleGrantedAuthority>> authorityListMono = accountDOMono
                .flatMapMany(accountDO -> authorityService.getAdminAuthority(accountDO.getId()))
                .map(authorityStr -> new SimpleGrantedAuthority(authorityStr))
                .collectList();

        return accountDOMono.zipWith(authorityListMono)
                .map(tuple -> {
                    AccountDO accountDO = tuple.getT1();
                    List<SimpleGrantedAuthority> authorityList = tuple.getT2();
                    AdminUserDetails adminUserDetails = AdminUserDetails.builder()
                            .id(accountDO.getId())
                            .username(accountDO.getUsername())
                            .password(accountDO.getPassword())
                            .random(accountDO.getRandom())
                            .authorities(authorityList)
                            .build();
                    return adminUserDetails;
                });

    }
}
