package com.jackmouse.system.blog.dataaccess.article.mapper;

import com.jackmouse.system.blog.dataaccess.article.entity.ArticleEntity;
import com.jackmouse.system.blog.dataaccess.article.entity.CategoryEntity;
import com.jackmouse.system.blog.dataaccess.article.entity.TagEntity;
import com.jackmouse.system.blog.domain.article.entity.Article;
import com.jackmouse.system.blog.domain.article.entity.Category;
import com.jackmouse.system.blog.domain.article.entity.Tag;
import com.jackmouse.system.blog.domain.article.query.ArticleSummary;
import com.jackmouse.system.blog.domain.article.valueobject.*;
import com.jackmouse.system.blog.domain.valueobject.ImageUrl;
import org.springframework.stereotype.Component;

/**
 * @ClassName ArticleDataAccessMapper
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/10 09:50
 * @Version 1.0
 **/
@Component
public class ArticleDataAccessMapper {
    public ArticleSummary articleEntityToArticleSummary(ArticleEntity articleEntity) {
        ArticleSummary summary = ArticleSummary.builder()
                .id(new ArticleId(articleEntity.getId()))
                .title(new ArticleTitle(articleEntity.getTitle()))
                .summary(new ArticleContent(articleEntity.getContent()))
                .cover(new ImageUrl(articleEntity.getCoverUrl()))
                .author(new AuthorInfo(articleEntity.getAuthorId(), articleEntity.getAuthorName()))
                .category(Category.builder()
                        .id(new CategoryId(articleEntity.getCategoryEntity().getId()))
                        .name(new CategoryName(articleEntity.getCategoryEntity().getName()))
                        .build()
                )
                .publishTime(articleEntity.getPublishTime())
                .stats(new ArticleStats(articleEntity.getViewCount(),
                        0, 0, articleEntity.getViewCount()))
                .tags(articleEntity.getTagEntities().stream().map(tagEntity ->
                                Tag.builder()
                                        .id(new TagId(tagEntity.getId()))
                                        .name(new TagName(tagEntity.getName()))
                                        .build()
                        )
                        .toList())
                .build();
        summary.generateHotScore();
        return summary;
    }

    public Article articleEntityToArticle(ArticleEntity articleEntity) {
        return Article.builder()
                .id(new ArticleId(articleEntity.getId()))
                .author(new AuthorInfo(articleEntity.getAuthorId(), articleEntity.getAuthorName()))
                .title(new ArticleTitle(articleEntity.getTitle()))
                .content(new ArticleContent(articleEntity.getContent()))
                .cover(new ImageUrl(articleEntity.getCoverUrl()))
                .category(Category.builder()
                        .id(new CategoryId(articleEntity.getCategoryEntity().getId()))
                        .name(new CategoryName(articleEntity.getCategoryEntity().getName()))
                        .build()
                )                .publishTime(articleEntity.getPublishTime())
                .stats(new ArticleStats(articleEntity.getViewCount(),
                        0, 0, articleEntity.getViewCount()))
                .tags(articleEntity.getTagEntities().stream().map(tagEntity ->
                                Tag.builder()
                                        .id(new TagId(tagEntity.getId()))
                                        .name(new TagName(tagEntity.getName()))
                                        .build()
                        )
                        .toList())
                .build();
    }

    public ArticleEntity articleToArticleEntity(Article article) {
        return ArticleEntity.builder()
                .id(article.getId().getValue())
                .title(article.getTitle().value())
                .content(article.getContent().value())
                .coverUrl(article.getCover().value())
                .categoryEntity(CategoryEntity.builder()
                        .id(article.getCategory().getId().getValue())
                        .build())
                .authorId(article.getAuthor().authorId())
                .authorName(article.getAuthor().authorName())
                .viewCount(article.getStats().readCount())
                .likeCount(article.getStats().likes())
                .status(article.getStatus())
                .tagEntities(article.getTags().stream().map(tag ->
                        TagEntity.builder()
                                .id(tag.getId().getValue())
                                .build()).toList())
                .build();
    }
}
