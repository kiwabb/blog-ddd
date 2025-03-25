package com.jackmouse.system.blog.application.article.ports.input.service;

import com.jackmouse.system.blog.application.article.dto.create.CreateArticleCommand;
import com.jackmouse.system.blog.application.article.dto.create.CreateArticleResponse;
import com.jackmouse.system.blog.application.article.dto.query.*;

import java.util.List;

/**
 * @ClassName ArticleApplicationService
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/7 14:22
 * @Version 1.0
 **/
public interface ArticleApplicationService {
    List<QueryMainSortCategoryArticlesResponse> queryMainSortCategoryArticles();

    ArticleResponse queryArticleById(ArticleIdQuery articleIdQuery);

    CreateArticleResponse createArticle(CreateArticleCommand createArticleCommand);

    List<CategoryResponse> queryCategories();

    List<TagResponse> queryTags();
}
