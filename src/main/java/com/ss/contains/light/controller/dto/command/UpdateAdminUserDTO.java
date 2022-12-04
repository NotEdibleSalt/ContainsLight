package com.ss.contains.light.controller.dto.command;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.util.Set;

/**
 * @version 1.0
 * @date 2022-5-2
 */
@Data
@EqualsAndHashCode
public class UpdateAdminUserDTO {

    /**
     * 邮箱
     */
    private String email;


    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    private String phone;


    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空")
    private String name;


    /**
     * 昵称
     */
    @NotBlank(message = "昵称不能为空")
    private String nickname;


    /**
     * 头像(相对路径)
     */
    private String avatar;


    /**
     * 性别
     */
    private String gender;

    /**
     * 角色
     */
    private Set<String> roles;


}
