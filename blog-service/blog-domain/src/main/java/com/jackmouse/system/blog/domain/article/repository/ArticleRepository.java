package com.jackmouse.system.blog.domain.article.repository;

import com.jackmouse.system.blog.domain.article.query.ArticleSummary;

import java.util.List;

/**
 * @ClassName ArticleRepository
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/7 13:29
 * @Version 1.0
 **/
public interface ArticleRepository {

    List<ArticleSummary> findPublishSortCategoryArticles();
}
