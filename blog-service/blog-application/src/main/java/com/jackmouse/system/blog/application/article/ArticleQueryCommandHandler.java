package com.jackmouse.system.blog.application.article;

import com.jackmouse.system.blog.application.article.dto.query.*;
import com.jackmouse.system.blog.application.article.mapper.ArticleDataMapper;
import com.jackmouse.system.blog.domain.article.entity.Article;
import com.jackmouse.system.blog.domain.article.query.ArticleSummary;
import com.jackmouse.system.blog.domain.article.repository.ArticleRepository;
import com.jackmouse.system.blog.domain.article.valueobject.ArticleId;
import com.jackmouse.system.blog.domain.exception.BlogNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.jackmouse.system.blog.application.article.dto.query.CategoryResponse.*;

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
        Article article = articleRepository.findById(new ArticleId(articleIdQuery.getArticleId()))
                .orElseThrow(() -> new BlogNotFoundException("Article not found id = " + articleIdQuery.getArticleId() + "."));
        article.generateHotScore();
        return articleDataMapper.articleToArticleResponse(article);
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> queryCategories() {
        return CategoryResponse.fromCategoryList(articleRepository.findCategories());
    }
    @Transactional(readOnly = true)
    public List<TagResponse> queryTags() {
        return TagResponse.fromTagList(articleRepository.findTags());
    }
}
