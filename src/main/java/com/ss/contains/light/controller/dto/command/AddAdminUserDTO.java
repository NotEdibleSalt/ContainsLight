package com.ss.contains.light.controller.dto.command;

import cn.hutool.core.bean.BeanUtil;
import com.ss.contains.light.common.exception.Ex;
import com.ss.contains.light.dos.AccountDO;
import com.ss.contains.light.dos.AdminUserDO;
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
public class AddAdminUserDTO {

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
     * 确认密码
     */
    @NotBlank(message = "确认密码不能为空")
    private String confirmPassword;

    /**
     * 角色
     */
    private Set<String> roles;

    public void validated() {

        if (!password.equals(confirmPassword)) {
            throw new Ex("密码和确认密码不一致");
        }
    }


    public AdminUserDO toAdminUserDO() {
        return BeanUtil.copyProperties(this, AdminUserDO.class);
    }

    public AccountDO toAccountDO() {
        return BeanUtil.copyProperties(this, AccountDO.class);
    }

}
