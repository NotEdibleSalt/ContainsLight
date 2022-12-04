package com.ss.contains.light.dos;

import com.ss.contains.light.base.DOBase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.util.Set;

/**
 * 后台管理用户DO
 *
 * @author NotEdibleSalt
 */

@Data
@Table("admin_user")
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserDO extends DOBase implements Serializable {

    /**
     * 账号id
     */
    private String accountId;


    /**
     * 邮箱
     */
    private String email;


    /**
     * 手机号
     */
    private String phone;


    /**
     * 姓名
     */
    private String name;


    /**
     * 昵称
     */
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
     * 角色集合
     */
    @Transient
    private Set<String> roles;
}
