package com.jackmouse.system.blog.application;

import com.jackmouse.system.blog.application.dto.query.ArticleIdQuery;
import com.jackmouse.system.blog.application.dto.query.ArticleResponse;
import com.jackmouse.system.blog.application.dto.query.QueryMainSortCategoryArticlesResponse;
import com.jackmouse.system.blog.application.mapper.ArticleDataMapper;
import com.jackmouse.system.blog.domain.article.entity.Article;
import com.jackmouse.system.blog.domain.article.query.ArticleSummary;
import com.jackmouse.system.blog.domain.article.repository.ArticleRepository;
import com.jackmouse.system.blog.domain.article.valueobject.ArticleId;
import com.jackmouse.system.blog.domain.exception.BlogNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * @ClassName ArticleQueryCommandHandler
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/14 10:51
 * @Version 1.0
 **/
@Slf4j
@Component
public class ArticleQueryCommandHandler {

    private final ArticleRepository articleRepository;
    private final ArticleDataMapper articleDataMapper;

    public ArticleQueryCommandHandler(ArticleRepository articleRepository, ArticleDataMapper articleDataMapper) {
        this.articleRepository = articleRepository;
        this.articleDataMapper = articleDataMapper;
    }

    @Transactional(readOnly = true)
    public List<QueryMainSortCategoryArticlesResponse> queryMainSortCategoryArticles() {
        List<ArticleSummary> articleSummaries = articleRepository.findPublishSortCategoryArticles();
        return articleDataMapper
                .articleSummaryListToQueryMainSortCategoryArticlesResponseList(articleSummaries);
    }

    @Transactional(readOnly = true)
    public ArticleResponse queryArticleById(ArticleIdQuery articleIdQuery) {
        Article article = articleRepository.findById(new ArticleId(articleIdQuery.getArticleId()));
        if (article == null) {
            log.warn("Article not found id = {}.", articleIdQuery.getArticleId());
            throw new BlogNotFoundException("Article not found id = " + articleIdQuery.getArticleId() + ".");
        }
        return articleDataMapper.articleToArticleResponse(article);
    }
}
