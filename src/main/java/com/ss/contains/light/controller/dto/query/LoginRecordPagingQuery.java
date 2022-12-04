package com.ss.contains.light.controller.dto.query;

import com.ss.contains.light.common.paging.PagingParam;
import lombok.Data;

/**
 * 分页查询登录记录参数
 *
 * @author NotEdibleSalt
 */
@Data
public class LoginRecordPagingQuery extends PagingParam {

    private String loginStatus;

    private String loginAccount;

    private String loginTime;


}
