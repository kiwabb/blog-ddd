package com.jackmouse.system.blog.dataaccess.article.entity;

import com.jackmouse.system.blog.domain.article.entity.Article;
import com.jackmouse.system.blog.domain.article.entity.Category;
import com.jackmouse.system.blog.domain.article.entity.Tag;
import com.jackmouse.system.blog.domain.article.valueobject.*;
import com.jackmouse.system.blog.domain.valueobject.Content;
import com.jackmouse.system.blog.domain.valueobject.ImageUrl;
import com.jackmouse.system.entity.ToData;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName Article
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/7 17:09
 * @Version 1.0
 **/
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "article", schema = "blog")
public class ArticleEntity implements ToData<Article> {
    @Id
    @Column(columnDefinition = "UUID")
    private UUID id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "cover_url", length = 512)
    private String coverUrl;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    private CategoryEntity categoryEntity;

    @Column(name = "author_id", nullable = false, length = 32)
    private Long authorId;

    @Column(name = "author_name", nullable = false, length = 64)
    private String authorName;

    @Column(name = "view_count", nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer viewCount = 0;

    @Column(name = "like_count", nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer likeCount = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ArticleStatus status;

    @Column(name = "publish_time")
    private LocalDateTime publishTime;

    @Column(name = "is_top", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isTop = false;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Version
    private Long version;

    @ManyToMany
    @JoinTable(
            name = "article_tag",
            schema = "blog",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<TagEntity> tagEntities;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @Override
    public Article toData() {
        return Article.builder()
                .id(new ArticleId(getId()))
                .author(new AuthorInfo(getAuthorId(), getAuthorName()))
                .title(new ArticleTitle(getTitle()))
                .content(new Content(getContent()))
                .cover(new ImageUrl(getCoverUrl()))
                .category(Category.builder()
                        .id(new CategoryId(getCategoryEntity().getId()))
                        .name(new CategoryName(getCategoryEntity().getName()))
                        .build()
                )                .publishTime(getPublishTime())
                .stats(new ArticleStats(getViewCount(),
                        0, 0, getViewCount()))
                .tags(getTagEntities().stream().map(tagEntity ->
                                Tag.builder()
                                        .id(new TagId(tagEntity.getId()))
                                        .name(new TagName(tagEntity.getName()))
                                        .build()
                        )
                        .toList())
                .build();
    }
}
