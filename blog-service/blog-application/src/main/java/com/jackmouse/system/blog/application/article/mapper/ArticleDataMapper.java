package com.jackmouse.system.blog.application.article.mapper;

import com.jackmouse.system.blog.application.article.dto.create.CreateArticleCommand;
import com.jackmouse.system.blog.application.article.dto.create.CreateArticleResponse;
import com.jackmouse.system.blog.application.article.dto.query.*;
import com.jackmouse.system.blog.domain.article.entity.Article;
import com.jackmouse.system.blog.domain.article.entity.Category;
import com.jackmouse.system.blog.domain.article.entity.Tag;
import com.jackmouse.system.blog.domain.article.query.ArticleSummary;
import com.jackmouse.system.blog.domain.article.valueobject.*;
import com.jackmouse.system.blog.domain.valueobject.ImageUrl;
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
                        .categoryId(articleSummary.getCategory().getId().getValue())
                        .categoryName(articleSummary.getCategory().getName().value())
                        .build())
                .tags(articleSummary.getTags().stream().map(tag ->
                                TagResponse.builder().tagId(tag.getId().getValue()).tagName(tag.getName().value()).build())
                        .toList())
                .build()).toList();
    }

    public ArticleResponse articleToArticleResponse(Article article) {
        return ArticleResponse.builder()
                .id(article.getId().getValue())
                .title(article.getTitle().value())
                .content(article.getContent().value())
                .cover(article.getCover().value())
                .author(article.getAuthor().authorName())
                .publishTime(article.getPublishTime())
                .category(CategoryResponse.builder()
                        .categoryId(article.getCategory().getId().getValue())
                        .categoryName(article.getCategory().getName().value())
                        .build())
                .tags(article.getTags().stream().map(tag ->
                                TagResponse.builder().tagId(tag.getId().getValue()).tagName(tag.getName().value()).build())
                        .toList())
                .hotScore(article.getHotScore().value())
                .build();
    }

    public Article createArticleCommandToArticle(CreateArticleCommand createArticleCommand) {
        return Article.builder()
                .title(new ArticleTitle(createArticleCommand.getTitle()))
                .content(new ArticleContent(createArticleCommand.getContent()))
                .cover(new ImageUrl(createArticleCommand.getCover()))
                .category(Category.builder()
                        .id(new CategoryId(createArticleCommand.getCategoryId())).build())
                .tags(createArticleCommand.getTagIds().stream().map(tagId -> Tag.builder()
                        .id(new TagId(tagId))
                        .build()).toList())
                .status(createArticleCommand.isDraft() ? ArticleStatus.DRAFT : ArticleStatus.PENDING_APPROVAL)
                .build()
                ;
    }

    public CreateArticleResponse articleToCreateArticleResponse(Article article) {
        return CreateArticleResponse.builder()
                .articleId(article.getId().getValue())
                .status(article.getStatus())
                .message("Article created successfully")
                .build();
    }
}
