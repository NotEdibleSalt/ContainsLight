package com.ss.contains.light.controller;

import com.ss.contains.light.common.MessageCommon;
import com.ss.contains.light.common.R;
import com.ss.contains.light.common.paging.PagingResult;
import com.ss.contains.light.controller.dto.command.SaveArticleDTO;
import com.ss.contains.light.controller.dto.command.SaveArticleTypeAndIntroductionApiDTO;
import com.ss.contains.light.controller.dto.query.ArticlesPagingQuery;
import com.ss.contains.light.dos.ArticleDO;
import com.ss.contains.light.service.ArticleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * 文章管理
 *
 * @author NotEdibleSalt
 */
@RequestMapping("articles")
@RestController
@AllArgsConstructor
public class ArticleController {


    private final ArticleService articleService;

    /**
     * 分页查询文章
     *
     * @param articlesPagingQuery
     * @return reactor.core.publisher.Mono<com.ss.contains.light.common.paging.PagingResult < com.ss.contains.light.dos.ArticleDO>>
     */
    @GetMapping()
    public Mono<PagingResult<ArticleDO>> articlePaging(ArticlesPagingQuery articlesPagingQuery) {

        return articleService.articlePaging(articlesPagingQuery);
    }

    /**
     * 通过id查询文章信息
     *
     * @param articleId 文章id
     * @return reactor.core.publisher.Mono<com.ss.contains.light.dos.ArticleDO>
     */
    @GetMapping("{articleId}")
    public Mono<ArticleDO> getArticleById(@PathVariable("articleId") String articleId) {

        return articleService.getArticleById(articleId);
    }


    /**
     * 新增文章
     *
     * @param saveArticleDTO
     * @return com.ss.contains.light.dos.ArticleDO
     */
    @PostMapping()
    public Mono<ArticleDO> addArticle(@RequestBody SaveArticleDTO saveArticleDTO) {

        return articleService.addArticle(saveArticleDTO);
    }

    /**
     * 更新文章
     *
     * @param articleId      文章id
     * @param saveArticleDTO
     * @return reactor.core.publisher.Mono<com.ss.contains.light.dos.ArticleDO>
     */
    @PutMapping("{articleId}")
    public Mono<ArticleDO> updateArticle(@PathVariable("articleId") String articleId, @RequestBody SaveArticleDTO saveArticleDTO) {

        return articleService.updateArticle(articleId, saveArticleDTO);
    }

    /**
     * 保存文章类型和简介
     *
     * @param articleId      文章id
     * @param saveArticleTypeAndIntroductionApiDTO
     * @return reactor.core.publisher.Mono<com.ss.contains.light.dos.ArticleDO>
     */
    @PatchMapping("{articleId}")
    public Mono<ArticleDO> saveArticleTypeAndIntroductionApi(@PathVariable("articleId") String articleId,
                                                             @RequestBody SaveArticleTypeAndIntroductionApiDTO saveArticleTypeAndIntroductionApiDTO) {

        return articleService.saveArticleTypeAndIntroductionApi(articleId, saveArticleTypeAndIntroductionApiDTO);
    }

    /**
     * 发布文章
     *
     * @param articleId 文章id
     * @return reactor.core.publisher.Mono<com.ss.contains.light.common.R>
     */
    @PutMapping("{articleId}/publish")
    public Mono<R> publishArticle(@PathVariable("articleId") String articleId) {

        return articleService.publishArticle(articleId).thenReturn(R.success());
    }

    /**
     * 下架文章
     *
     * @param articleId 文章id
     * @return reactor.core.publisher.Mono<com.ss.contains.light.common.R>
     */
    @PutMapping("{articleId}/lower-shelf")
    public Mono<R> lowerShelfArticle(@PathVariable("articleId") String articleId) {

        return articleService.lowerShelfArticle(articleId).thenReturn(R.success());
    }

    /**
     * 删除文章
     *
     * @param articleId 文章id
     * @return reactor.core.publisher.Mono<com.ss.contains.light.common.R < java.lang.Object>>
     */
    @DeleteMapping("{articleId}")
    public Mono<R> delArticle(@PathVariable("articleId") String articleId) {

        return articleService.delArticle(articleId).thenReturn(R.message(MessageCommon.DEL_SUCCESS));
    }
}
