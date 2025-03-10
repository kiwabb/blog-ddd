package com.jackmouse.system.blog.domain.article;

import com.jackmouse.system.blog.domain.article.exception.BlogDomainException;
import com.jackmouse.system.blog.domain.article.query.ArticleSummary;
import com.jackmouse.system.blog.domain.article.repository.ArticleRepository;
import com.jackmouse.system.blog.domain.article.repository.CategoryRepository;

import java.util.List;

public class ArticleQueryServiceImpl implements ArticleQueryService {
    
    private final ArticleRepository articleRepository;

    public ArticleQueryServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public List<ArticleSummary> queryMainSortCategoryArticles() {
        // 调用仓储接口获取数据
        return articleRepository.findPublishSortCategoryArticles();
    }
}