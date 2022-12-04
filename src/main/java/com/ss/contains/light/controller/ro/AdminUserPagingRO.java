package com.ss.contains.light.controller.ro;

import lombok.Data;

import java.io.Serializable;

/**
 * 分页查询管理员用户结果
 */
@Data
public class AdminUserPagingRO implements Serializable {

    /**
     * 后台管理用户id
     */
    private String id;


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
    private Object gender;

    /**
     * 用户名
     */
    private String username;

    /**
     * 状态 1:enable, 0:disable
     */
    private String status;
}
