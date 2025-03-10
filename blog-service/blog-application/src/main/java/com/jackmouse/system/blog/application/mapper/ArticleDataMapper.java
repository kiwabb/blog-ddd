package com.jackmouse.system.blog.application.mapper;

import com.jackmouse.system.blog.application.dto.query.ArticleSummaryResponse;
import com.jackmouse.system.blog.application.dto.query.CategoryResponse;
import com.jackmouse.system.blog.application.dto.query.QueryMainSortCategoryArticlesResponse;
import com.jackmouse.system.blog.application.dto.query.TagResponse;
import com.jackmouse.system.blog.domain.article.query.ArticleSummary;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName ArticleDataMapper
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/7 14:50
 * @Version 1.0
 **/
@Component
public class ArticleDataMapper {
    public List<QueryMainSortCategoryArticlesResponse>
    articleSummaryListToQueryMainSortCategoryArticlesResponseList(List<ArticleSummary> articleSummaryList) {
        List<ArticleSummaryResponse> articleSummaries =
                articleSummaryEntityListToArticleSummaryList(articleSummaryList);

        // 按 Category 分组
        Map<CategoryResponse, List<ArticleSummaryResponse>> groupedByCategory =
                articleSummaries.stream()
                        .collect(Collectors.groupingBy(ArticleSummaryResponse::getCategory));


        // 构建 QueryMainSortCategoryArticlesResponse 列表
        return groupedByCategory.entrySet().stream()
                .map(entry -> QueryMainSortCategoryArticlesResponse.builder()
                        .category(entry.getKey()) // 设置 Category
                        .articles(entry.getValue()) // 设置对应的文章列表
                        .build())
                .toList();

    }

    private List<ArticleSummaryResponse>
    articleSummaryEntityListToArticleSummaryList(List<ArticleSummary> articleSummaryList) {

        return articleSummaryList.stream().map(articleSummary -> ArticleSummaryResponse.builder()
                .id(articleSummary.getId().getValue())
                .title(articleSummary.getTitle().value())
                .summary(articleSummary.getSummary().generateSummary(50).value())
                .cover(articleSummary.getCover().value())
                .author(articleSummary.getAuthor().authorName())
                .hotScore(articleSummary.getHotScore().value())
                .publishTime(articleSummary.getPublishTime())
                .likeCount(articleSummary.getStats().likes())
                .favoriteCount(articleSummary.getStats().favorites())
                .commentCount(articleSummary.getStats().commentCount())
                .readCount(articleSummary.getStats().readCount())
                .category(CategoryResponse.builder()
                        .categoryId(articleSummary.getCategory().getId())
                        .categoryName(articleSummary.getCategory().getName().value())
                        .build())
                .tags(articleSummary.getTags().stream().map(tag ->
                                TagResponse.builder().tagId(tag.getId()).tagName(tag.getName().value()).build())
                        .toList())
                .build()).toList();
    }
}
