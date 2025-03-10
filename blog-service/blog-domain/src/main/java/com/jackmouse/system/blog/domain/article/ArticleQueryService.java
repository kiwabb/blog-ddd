package com.jackmouse.system.blog.domain.article;

import com.jackmouse.system.blog.domain.article.query.ArticleSummary;

import java.util.List;

/**
 * @ClassName ArticleQueryService
 * @Description 文章查询服务
 * @Author zhoujiaangyao
 * @Date 2025/3/7 11:40
 * @Version 1.0
 **/
public interface ArticleQueryService {
    List<ArticleSummary> queryMainSortCategoryArticles();
}
