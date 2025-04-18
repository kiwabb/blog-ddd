package com.jackmouse.system.blog.domain.article.entity;

import com.jackmouse.system.blog.domain.article.valueobject.*;
import com.jackmouse.system.blog.domain.entity.AggregateRoot;
import com.jackmouse.system.blog.domain.article.valueobject.ArticleStats;
import com.jackmouse.system.blog.domain.exception.BlogDomainException;
import com.jackmouse.system.blog.domain.valueobject.Content;
import com.jackmouse.system.blog.domain.valueobject.ImageUrl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName Article
 * @Description 文章实体(聚合)
 * @Author zhoujiaangyao
 * @Date 2025/3/7 10:22
 * @Version 1.0
 **/
public class Article extends AggregateRoot<ArticleId> {

    private AuthorInfo author;
    private final ArticleTitle title;
    private final Content content;
    private final ImageUrl cover;
    private final Category category;
    private final List<Tag> tags;
    private final LocalDateTime publishTime;
    private ArticleStats stats;
    private HotScore hotScore;

    private ArticleStatus status;

    private Article(Builder builder) {
        super.setId(builder.articleId);
        author = builder.author;
        title = builder.title;
        content = builder.content;
        cover = builder.cover;
        category = builder.category;
        tags = builder.tags;
        stats = builder.stats;
        status = builder.status;
        publishTime = builder.publishTime;
    }

    public void generateHotScore() {
        hotScore = HotScore.calculate(stats, publishTime);
    }
    public static Builder builder() {
        return new Builder();
    }

    public LocalDateTime getPublishTime() {
        return publishTime;
    }

    public AuthorInfo getAuthor() {
        return author;
    }

    public ArticleTitle getTitle() {
        return title;
    }

    public Content getContent() {
        return content;
    }

    public ImageUrl getCover() {
        return cover;
    }

    public Category getCategory() {
        return category;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public ArticleStats getStats() {
        return stats;
    }

    public ArticleStatus getStatus() {
        return status;
    }
    public HotScore getHotScore() {
        return hotScore;
    }

    public void validateArticle() {
        validateInitialArticle();
    }

    private void validateInitialArticle() {
        if (getId() != null || (status != ArticleStatus.PENDING_APPROVAL  && status != ArticleStatus.DRAFT)) {
            throw new BlogDomainException("Article is not in correct state for initialization!");
        }
    }

    public void initializeArticle() {
        setId(new ArticleId(UUID.randomUUID()));
        stats = ArticleStats.initializeStats();
        author = new AuthorInfo(1L, "admin");
    }

    public static final class Builder {
        private ArticleId articleId;
        private AuthorInfo author;
        private ArticleTitle title;
        private Content content;
        private ImageUrl cover;
        private Category category;
        private List<Tag> tags;
        private ArticleStats stats;
        private ArticleStatus status;
        private LocalDateTime publishTime;

        private Builder() {
        }

        public Builder id(ArticleId val) {
            articleId = val;
            return this;
        }

        public Builder author(AuthorInfo val) {
            author = val;
            return this;
        }

        public Builder title(ArticleTitle val) {
            title = val;
            return this;
        }

        public Builder content(Content val) {
            content = val;
            return this;
        }

        public Builder cover(ImageUrl val) {
            cover = val;
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

        public Builder stats(ArticleStats val) {
            stats = val;
            return this;
        }

        public Builder status(ArticleStatus val) {
            status = val;
            return this;
        }

        public Article build() {
            return new Article(this);
        }

        public Builder publishTime(LocalDateTime val) {
            publishTime = val;
            return this;
        }
    }
}
