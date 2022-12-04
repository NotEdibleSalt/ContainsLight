package com.ss.contains.light.dos;

import com.ss.contains.light.base.DOBase;
import com.ss.contains.light.common.exception.Ex;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

/**
 * 文章DO
 *
 * @author NotEdibleSalt
 * @date 2022-5-3
 */

@Data
@Table("article")
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDO extends DOBase implements Serializable {

    /**
     * 标题
     */
    private String title;


    /**
     * 类型
     */
    private ArticleTypeEnum articleType;


    /**
     * 状态
     */
    private ArticleStatusEnum articleStatus;


    /**
     * 简介
     */
    private String introduction;


    /**
     * 内容
     */
    private String content;

    /**
     * 发布文章
     *
     * @param
     * @return com.ss.contains.light.dos.ArticleDO
     */
    public ArticleDO publish() {

        if (getArticleStatus() == ArticleStatusEnum.PUBLISHING) {
            throw new Ex("文章已被发布");
        }
        this.setArticleStatus(ArticleStatusEnum.PUBLISHING);
        return this;
    }

    /**
     * 下架文章
     *
     * @param
     * @return com.ss.contains.light.dos.ArticleDO
     */
    public ArticleDO lowerShelf() {

        if (getArticleStatus() == ArticleStatusEnum.PUBLISHING) {
            this.setArticleStatus(ArticleStatusEnum.LOWER_SHELF);
            return this;
        }
        throw new Ex("文章未处于发布状态，无法下架");
    }


    /**
     * 文章状态
     */
    @Getter
    @AllArgsConstructor
    public enum ArticleStatusEnum {

        DRAFT("草稿"),
        PUBLISHING("发布中"),
        LOWER_SHELF("下架");

        private final String des;

    }


    /**
     * 文章类型
     */
    @Getter
    @AllArgsConstructor
    public enum ArticleTypeEnum {

        DEFAULT("默认"),
        RECIPES("食谱"),
        POLICY("政策");

        private final String des;
    }

}


