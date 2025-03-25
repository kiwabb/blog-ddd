package com.jackmouse.system.blog.application.article.dto.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName ArticleResponse
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/14 10:07
 * @Version 1.0
 **/
@Getter
@Builder
@AllArgsConstructor
@Schema(description = "文章详情响应对象")
public class ArticleResponse {
    @Schema(description = "文章ID", example = "550e8400-e29b-41d4-a716-446655440000")
    private final UUID id;

    @Schema(description = "文章标题", example = "Spring Boot最佳实践")
    private final String title;

    @Schema(description = "内容", example = "本文详细讲解Spring Boot的核心特性...")
    private final String content;

    @Schema(description = "封面图URL", example = "/covers/spring-boot.jpg")
    private final String cover;

    @Schema(description = "作者名称", example = "张三")
    private final String author;

    @Schema(description = "发布时间", example = "2025-03-07T10:00:00")
    private final LocalDateTime publishTime;

    @Schema(description = "所属分类", implementation = CategoryResponse.class)
    private final CategoryResponse category;

    @Schema(description = "关联标签列表", example = "[\"技术\", \"后端\"]")
    private final List<TagResponse> tags;

    @Schema(description = "热度评分（算法计算）", example = "7.8")
    private final double hotScore;

    @Schema(description = "点赞数", minimum = "0", example = "256")
    private final int likeCount;

    @Schema(description = "收藏数", minimum = "0", example = "89")
    private final int favoriteCount;

    @Schema(description = "评论数", minimum = "0", example = "34")
    private final int commentCount;

    @Schema(description = "阅读量", minimum = "0", example = "1024")
    private final int readCount;
}
