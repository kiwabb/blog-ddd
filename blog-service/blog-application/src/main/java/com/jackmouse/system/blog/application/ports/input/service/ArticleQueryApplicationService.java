package com.jackmouse.system.blog.application.ports.input.service;

import com.jackmouse.system.blog.application.dto.query.QueryMainSortCategoryArticlesResponse;

import java.util.List;

/**
 * @ClassName ArticleApplicationService
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/7 14:22
 * @Version 1.0
 **/
public interface ArticleQueryApplicationService {
    List<QueryMainSortCategoryArticlesResponse> queryMainSortCategoryArticles();
}
