package com.ss.contains.light.controller.dto.query;

import com.ss.contains.light.common.paging.PagingParam;
import lombok.Data;

/**
 * 分页查询字典参数
 *
 * @version 1.0
 * @date 2022-5-2
 */
@Data
public class DictsPagingQuery extends PagingParam {

    /**
     * 字典名
     */
    private String name;

    /**
     * 字典类型
     */
    private String type;

}
