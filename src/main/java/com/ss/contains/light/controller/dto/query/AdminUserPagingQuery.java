package com.ss.contains.light.controller.dto.query;

import com.ss.contains.light.common.paging.PagingParam;
import lombok.Data;

/**
 * 管理员用户分页查询参数
 *
 * @version 1.0
 */
@Data
public class AdminUserPagingQuery extends PagingParam {

    /**
     * 姓名
     */
    private String name;

    /**
     * 用户名
     */
    private String username;

    /**
     * 手机号
     */
    private String phone;

}
