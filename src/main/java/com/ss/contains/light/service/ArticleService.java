package com.ss.contains.light.service;

import com.ss.contains.light.common.paging.PagingResult;
import com.ss.contains.light.controller.dto.command.SaveArticleDTO;
import com.ss.contains.light.controller.dto.command.SaveArticleTypeAndIntroductionApiDTO;
import com.ss.contains.light.controller.dto.query.ArticlesPagingQuery;
import com.ss.contains.light.dos.ArticleDO;
import reactor.core.publisher.Mono;

/**
 * @author NotEdibleSalt
 */
public interface ArticleService {

    /**
     * 分页查询文章
     *
     * @param articlesPagingQuery
     * @return reactor.core.publisher.Mono<com.ss.contains.light.common.paging.PagingResult < com.ss.contains.light.dos.ArticleDO>>
     */
    Mono<PagingResult<ArticleDO>> articlePaging(ArticlesPagingQuery articlesPagingQuery);

    /**
     * 通过id查询文章信息
     *
     * @param articleId 文章id
     * @return reactor.core.publisher.Mono<com.ss.contains.light.dos.ArticleDO>
     */
    Mono<ArticleDO> getArticleById(String articleId);

    /**
     * 新增文章
     *
     * @param saveArticleDTO
     * @return reactor.core.publisher.Mono<com.ss.contains.light.dos.ArticleDO>
     */
    Mono<ArticleDO> addArticle(SaveArticleDTO saveArticleDTO);

    /**
     * 更新文章
     *
     * @param articleId      文章id
     * @param saveArticleDTO
     * @return reactor.core.publisher.Mono<com.ss.contains.light.dos.ArticleDO>
     */
    Mono<ArticleDO> updateArticle(String articleId, SaveArticleDTO saveArticleDTO);

    /**
     * 保存文章类型和简介
     *
     * @param articleId      文章id
     * @param saveArticleTypeAndIntroductionApiDTO
     * @return reactor.core.publisher.Mono<com.ss.contains.light.dos.ArticleDO>
     */
    Mono<ArticleDO> saveArticleTypeAndIntroductionApi(String articleId,
                                                      SaveArticleTypeAndIntroductionApiDTO saveArticleTypeAndIntroductionApiDTO);


    /**
     * 发布文章
     *
     * @param articleId 文章id
     * @return reactor.core.publisher.Mono<com.ss.contains.light.dos.ArticleDO>
     */
    Mono<ArticleDO> publishArticle(String articleId);

    /**
     * 下架文章
     *
     * @param articleId 文章id
     * @return reactor.core.publisher.Mono<com.ss.contains.light.dos.ArticleDO>
     */
    Mono<ArticleDO> lowerShelfArticle(String articleId);

    /**
     * 删除文章
     *
     * @param articleId 文章id
     * @return reactor.core.publisher.Mono<com.ss.contains.light.dos.ArticleDO>
     */
    Mono<ArticleDO> delArticle(String articleId);



}
