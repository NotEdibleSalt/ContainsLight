package com.ss.contains.light.service.impl;

import cn.hutool.core.util.StrUtil;
import com.ss.contains.light.common.paging.PagingResult;
import com.ss.contains.light.common.paging.TemplateFunction;
import com.ss.contains.light.controller.dto.command.SaveArticleDTO;
import com.ss.contains.light.controller.dto.command.SaveArticleTypeAndIntroductionApiDTO;
import com.ss.contains.light.controller.dto.query.ArticlesPagingQuery;
import com.ss.contains.light.dos.ArticleDO;
import com.ss.contains.light.repository.ArticleRepo;
import com.ss.contains.light.service.ArticleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.List;


/**
 * @author NotEdibleSalt
 */
@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepo articleRepo;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    @Override
    public Mono<PagingResult<ArticleDO>> articlePaging(ArticlesPagingQuery articlesPagingQuery) {

        List<Criteria> criteriaList = TemplateFunction.baseQuery();
        if (StrUtil.isNotBlank(articlesPagingQuery.getArticleType())) {
            Criteria type = Criteria.where("article_type").is(articlesPagingQuery.getArticleType());
            criteriaList.add(type);
        }
        if (StrUtil.isNotBlank(articlesPagingQuery.getArticleStatus())) {
            Criteria type = Criteria.where("article_status").is(articlesPagingQuery.getArticleStatus());
            criteriaList.add(type);
        }

        Query query = Query.query(Criteria.from(criteriaList));

        TemplateFunction<ArticleDO> templateFun = () -> r2dbcEntityTemplate.select(ArticleDO.class).from("article");
        return PagingResult.templatePaging(templateFun).param(articlesPagingQuery.toPageable(), query);
    }

    @Override
    public Mono<ArticleDO> getArticleById(String articleId) {

        return articleRepo.findById(articleId);
    }

    @Override
    public Mono<ArticleDO> addArticle(SaveArticleDTO saveArticleDTO) {

        ArticleDO articleDO = saveArticleDTO.to();
        return articleRepo.save(articleDO);
    }

    @Override
    public Mono<ArticleDO> updateArticle(String articleId, SaveArticleDTO saveArticleDTO) {

        return articleRepo.findById(articleId)
                .map(articleDO -> {
                    articleDO.setTitle(saveArticleDTO.getTitle());
                    articleDO.setContent(saveArticleDTO.getContent());
                    return articleDO;
                })
                .flatMap(articleRepo::save);
    }

    @Override
    public Mono<ArticleDO> saveArticleTypeAndIntroductionApi(String articleId,
                                                             SaveArticleTypeAndIntroductionApiDTO saveArticleTypeAndIntroductionApiDTO) {

        return articleRepo.findById(articleId)
                .map(articleDO -> {
                    articleDO.setIntroduction(saveArticleTypeAndIntroductionApiDTO.getIntroduction());
                    articleDO.setArticleType(saveArticleTypeAndIntroductionApiDTO.getArticleType());
                    return articleDO;
                })
                .flatMap(articleRepo::save);
    }

    @Override
    public Mono<ArticleDO> publishArticle(String articleId) {

        return articleRepo.findById(articleId)
                .map(ArticleDO::publish)
                .flatMap(articleRepo::save);
    }

    @Override
    public Mono<ArticleDO> lowerShelfArticle(String articleId) {

        return articleRepo.findById(articleId)
                .map(ArticleDO::lowerShelf)
                .flatMap(articleRepo::save);
    }

    @Override
    public Mono<ArticleDO> delArticle(String articleId) {

        return articleRepo.logicDelete(articleId);
    }
}

