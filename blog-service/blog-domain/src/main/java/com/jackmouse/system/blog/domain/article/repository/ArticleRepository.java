package com.jackmouse.system.blog.domain.article.repository;

import com.jackmouse.system.blog.domain.article.entity.Article;
import com.jackmouse.system.blog.domain.article.entity.Category;
import com.jackmouse.system.blog.domain.article.entity.Tag;
import com.jackmouse.system.blog.domain.article.query.ArticleSummary;
import com.jackmouse.system.blog.domain.article.valueobject.ArticleId;

import java.util.List;
import java.util.Optional;

/**
 * @ClassName ArticleRepository
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/7 13:29
 * @Version 1.0
 **/
public interface ArticleRepository {

    List<ArticleSummary> findPublishSortCategoryArticles();

    Optional<Article> findById(ArticleId id);

    Boolean existById(ArticleId id);

    Article save(Article article);

    List<Category> findCategories();

    List<Tag> findTags();
}
