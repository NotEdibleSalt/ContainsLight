package com.ss.contains.light.controller.dto.query;

import com.ss.contains.light.common.enums.MethodEnum;
import com.ss.contains.light.common.paging.PagingParam;
import lombok.Data;

/**
 * 分页查询权限
 *
 * @author NotEdibleSalt
 */
@Data
public class AuthoritysPagingQuery extends PagingParam {

    private String name;

    private String module;

    private MethodEnum method;


}
