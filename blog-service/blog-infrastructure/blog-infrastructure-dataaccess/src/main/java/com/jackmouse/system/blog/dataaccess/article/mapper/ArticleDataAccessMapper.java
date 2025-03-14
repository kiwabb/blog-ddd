package com.jackmouse.system.blog.dataaccess.article.mapper;

import com.jackmouse.system.blog.dataaccess.article.entity.ArticleEntity;
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
                        .id(articleEntity.getCategoryEntity().getId())
                        .name(new CategoryName(articleEntity.getCategoryEntity().getName()))
                        .build()
                )
                .publishTime(articleEntity.getPublishTime())
                .stats(new ArticleStats(articleEntity.getViewCount(),
                        0, 0, articleEntity.getViewCount()))
                .tags(articleEntity.getTagEntities().stream().map(tagEntity ->
                                Tag.builder()
                                        .id(tagEntity.getId())
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
                        .id(articleEntity.getCategoryEntity().getId())
                        .name(new CategoryName(articleEntity.getCategoryEntity().getName()))
                        .build()
                )                .publishTime(articleEntity.getPublishTime())
                .stats(new ArticleStats(articleEntity.getViewCount(),
                        0, 0, articleEntity.getViewCount()))
                .tags(articleEntity.getTagEntities().stream().map(tagEntity ->
                                Tag.builder()
                                        .id(tagEntity.getId())
                                        .name(new TagName(tagEntity.getName()))
                                        .build()
                        )
                        .toList())
                .build();
    }
}
