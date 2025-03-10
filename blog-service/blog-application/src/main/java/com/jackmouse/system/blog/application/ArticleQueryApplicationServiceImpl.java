package com.jackmouse.system.blog.application;

import com.jackmouse.system.blog.application.dto.query.QueryMainSortCategoryArticlesResponse;
import com.jackmouse.system.blog.application.mapper.ArticleDataMapper;
import com.jackmouse.system.blog.application.ports.input.service.ArticleQueryApplicationService;
import com.jackmouse.system.blog.domain.article.ArticleQueryService;
import com.jackmouse.system.blog.domain.article.query.ArticleSummary;
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
public class ArticleQueryApplicationServiceImpl implements ArticleQueryApplicationService {

    private final ArticleQueryService articleQueryService;
    private final ArticleDataMapper articleDataMapper;

    public ArticleQueryApplicationServiceImpl(ArticleQueryService articleQueryService, ArticleDataMapper articleDataMapper) {
        this.articleQueryService = articleQueryService;
        this.articleDataMapper = articleDataMapper;
    }

    @Transactional(readOnly = true)
    @Override
    public List<QueryMainSortCategoryArticlesResponse> queryMainSortCategoryArticles() {
        List<ArticleSummary> articleSummaries = articleQueryService.queryMainSortCategoryArticles();
        return articleDataMapper
                .articleSummaryListToQueryMainSortCategoryArticlesResponseList(articleSummaries);
    }
}
