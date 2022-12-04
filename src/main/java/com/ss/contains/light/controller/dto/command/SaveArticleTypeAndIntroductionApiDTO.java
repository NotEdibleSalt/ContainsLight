package com.ss.contains.light.controller.dto.command;

import com.ss.contains.light.dos.ArticleDO;
import lombok.Data;

/**
 * @author NotEdibleSalt
 */
@Data
public class SaveArticleTypeAndIntroductionApiDTO {

    /**
     * 类型
     */
    private ArticleDO.ArticleTypeEnum articleType;



    /**
     * 简介
     */
    private String introduction;
}
