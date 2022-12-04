package com.ss.contains.light.controller.dto.query;

import com.ss.contains.light.common.paging.PagingParam;
import lombok.Data;

/**
 * 分页查询角色参数
 *
 * @author NotEdibleSalt
 */
@Data
public class RolesPagingQuery extends PagingParam {

    /**
     * 角色名
     */
    private String name;

}
