package com.ss.contains.light.controller.dto.query;

import com.ss.contains.light.common.paging.PagingParam;
import lombok.Data;

/**
 * 分页查询文章参数
 *
 * @author NotEdibleSalt
 */
@Data
public class ArticlesPagingQuery extends PagingParam {

    /**
     * 文章类型
     */
    private String articleType;

    /**
     * 文章状态
     */
    private String articleStatus;

}
