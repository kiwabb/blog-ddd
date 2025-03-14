package com.jackmouse.system.blog.application;

import com.jackmouse.system.blog.application.dto.query.ArticleIdQuery;
import com.jackmouse.system.blog.application.dto.query.ArticleResponse;
import com.jackmouse.system.blog.application.dto.query.QueryMainSortCategoryArticlesResponse;
import com.jackmouse.system.blog.application.ports.input.service.ArticleApplicationService;
import com.jackmouse.system.blog.domain.article.entity.Article;
import com.jackmouse.system.blog.domain.article.query.ArticleSummary;
import com.jackmouse.system.blog.domain.article.valueobject.ArticleId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * @ClassName ArticleQueryApplicationServiceImpl
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/7 14:28
 * @Version 1.0
 **/
@Validated
@Service
public class ArticleApplicationServiceImpl implements ArticleApplicationService {

    private final ArticleQueryCommandHandler articleQueryCommandHandler;

    public ArticleApplicationServiceImpl(ArticleQueryCommandHandler articleQueryCommandHandler) {

        this.articleQueryCommandHandler = articleQueryCommandHandler;
    }

    @Transactional(readOnly = true)
    @Override
    public List<QueryMainSortCategoryArticlesResponse> queryMainSortCategoryArticles() {
        return articleQueryCommandHandler.queryMainSortCategoryArticles();
    }

    @Transactional(readOnly = true)
    @Override
    public ArticleResponse queryArticleById(ArticleIdQuery articleIdQuery) {
        return articleQueryCommandHandler.queryArticleById(articleIdQuery);
    }
}
