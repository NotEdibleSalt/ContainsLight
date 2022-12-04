package com.ss.contains.light.config.security;

import cn.hutool.core.util.ObjectUtil;
import com.ss.contains.light.common.exception.Ex;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author NotEdibleSalt
 * @version 1.0
 */
@Data
@Builder
public class AdminUserDetails implements UserDetails {

    private String username;
    private String id;
    private String password;
    private String random;
    private List<SimpleGrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (ObjectUtil.isEmpty(authorities)) {
            return new ArrayList<>();
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * 检查密码是否正确
     *
     * @param password 密码
     * @return void
     * @date 2022-5-1
     **/
    public void checkPassword(String password) {

        boolean matches = AuthUtil.getPasswordEncoder()
                .matches(password + getRandom(), getPassword());
        if (!matches) {
            throw new Ex("密码错误");
        }
    }

    /**
     * 是否是超管
     *
     * @return boolean
     */
    public boolean isSuperAdmin(){

        return "admin".equals(this.getUsername());
    }

}
