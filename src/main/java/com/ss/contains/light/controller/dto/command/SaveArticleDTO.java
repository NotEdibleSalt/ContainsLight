package com.ss.contains.light.controller.dto.command;

import com.ss.contains.light.dos.ArticleDO;
import lombok.Data;

/**
 * 保存文章内容DTO
 *
 * @author NotEdibleSalt
 */
@Data
public class SaveArticleDTO {

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    public ArticleDO to() {

        ArticleDO articleDO = new ArticleDO();
        articleDO.setArticleStatus(ArticleDO.ArticleStatusEnum.DRAFT);
        articleDO.setTitle(this.title);
        articleDO.setContent(this.content);
        return articleDO;
    }

}
