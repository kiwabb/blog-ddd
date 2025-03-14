package com.jackmouse.system.blog.domain.article.query;

import com.jackmouse.system.blog.domain.article.entity.Article;
import com.jackmouse.system.blog.domain.article.entity.Category;
import com.jackmouse.system.blog.domain.article.entity.Tag;
import com.jackmouse.system.blog.domain.article.valueobject.*;
import com.jackmouse.system.blog.domain.article.valueobject.ArticleStats;
import com.jackmouse.system.blog.domain.valueobject.ImageUrl;

import java.time.LocalDateTime;
import java.util.List;

public class ArticleSummary {
    private final ArticleId id;
    private final ArticleTitle title;
    private final ArticleContent summary;
    private final ImageUrl cover;
    private final AuthorInfo author;
    private HotScore hotScore;
    private final ArticleStats stats;
    private final Category category;
    private final List<Tag> tags;
    private final LocalDateTime publishTime;

    private ArticleSummary(Builder builder) {
        id = builder.id;
        title = builder.title;
        summary = builder.summary;
        cover = builder.cover;
        author = builder.author;
        hotScore = builder.hotScore;
        stats = builder.stats;
        category = builder.category;
        tags = builder.tags;
        publishTime = builder.publishTime;
    }

    public static Builder builder() {
        return new Builder();
    }


    public ArticleId getId() {
        return id;
    }

    public ArticleTitle getTitle() {
        return title;
    }

    public ArticleContent getSummary() {
        return summary;
    }

    public ImageUrl getCover() {
        return cover;
    }

    public AuthorInfo getAuthor() {
        return author;
    }

    public HotScore getHotScore() {
        return hotScore;
    }

    public ArticleStats getStats() {
        return stats;
    }

    public Category getCategory() {
        return category;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public LocalDateTime getPublishTime() {
        return publishTime;
    }

    public ArticleSummary(ArticleId id, ArticleTitle title, ArticleContent summary, ImageUrl cover, AuthorInfo author, HotScore hotScore, ArticleStats stats, Category category, List<Tag> tags, LocalDateTime publishTime) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.cover = cover;
        this.author = author;
        this.hotScore = hotScore;
        this.stats = stats;
        this.category = category;
        this.tags = tags;
        this.publishTime = publishTime;
    }

    public static ArticleSummary from(Article article) {
        return new ArticleSummary(
                article.getId(),
                article.getTitle(),
                article.getContent().generateSummary(200), // 内容摘要
                article.getCover(),
                article.getAuthor(),
                HotScore.calculate(article.getStats(), article.getPublishTime()),
                article.getStats(),
                article.getCategory(),
                article.getTags(),
                article.getPublishTime());
    }

    public void generateHotScore() {
        hotScore = HotScore.calculate(stats, publishTime);
    }


    public static final class Builder {
        private ArticleId id;
        private ArticleTitle title;
        private ArticleContent summary;
        private ImageUrl cover;
        private AuthorInfo author;
        private HotScore hotScore;
        private ArticleStats stats;
        private Category category;
        private List<Tag> tags;
        private LocalDateTime publishTime;

        private Builder() {
        }

        public Builder id(ArticleId val) {
            id = val;
            return this;
        }

        public Builder title(ArticleTitle val) {
            title = val;
            return this;
        }

        public Builder summary(ArticleContent val) {
            summary = val;
            return this;
        }

        public Builder cover(ImageUrl val) {
            cover = val;
            return this;
        }

        public Builder author(AuthorInfo val) {
            author = val;
            return this;
        }

        public Builder hotScore(HotScore val) {
            hotScore = val;
            return this;
        }

        public Builder stats(ArticleStats val) {
            stats = val;
            return this;
        }

        public Builder category(Category val) {
            category = val;
            return this;
        }

        public Builder tags(List<Tag> val) {
            tags = val;
            return this;
        }

        public Builder publishTime(LocalDateTime val) {
            publishTime = val;
            return this;
        }

        public ArticleSummary build() {
            return new ArticleSummary(this);
        }
    }

    @Override
    public String toString() {
        return "ArticleSummary{" +
                "id=" + id +
                ", title=" + title +
                ", summary=" + summary +
                ", cover=" + cover +
                ", author=" + author +
                ", hotScore=" + hotScore +
                ", stats=" + stats +
                ", category=" + category +
                ", tags=" + tags +
                ", publishTime=" + publishTime +
                '}';
    }
}