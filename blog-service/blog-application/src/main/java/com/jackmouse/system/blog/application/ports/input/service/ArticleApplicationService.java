package com.jackmouse.system.blog.application.ports.input.service;

import com.jackmouse.system.blog.application.dto.create.CreateArticleCommand;
import com.jackmouse.system.blog.application.dto.create.CreateArticleResponse;
import com.jackmouse.system.blog.application.dto.query.ArticleIdQuery;
import com.jackmouse.system.blog.application.dto.query.ArticleResponse;
import com.jackmouse.system.blog.application.dto.query.QueryMainSortCategoryArticlesResponse;

import java.util.List;
import java.util.UUID;

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
}
