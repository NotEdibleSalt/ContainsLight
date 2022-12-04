package com.ss.contains.light.controller.dto.command;

import com.ss.contains.light.util.AESUtil;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author NotEdibleSalt
 * @version 1.0
 */
@Data
public class LoginDTO {

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 解密
     *
     * @return java.lang.String
     * @author NotEdibleSalt
     */
    public LoginDTO decrypt() {

        this.setUsername(AESUtil.decrypt(this.getUsername()));
        this.setPassword(AESUtil.decrypt(this.getPassword()));

        return this;
    }
}
