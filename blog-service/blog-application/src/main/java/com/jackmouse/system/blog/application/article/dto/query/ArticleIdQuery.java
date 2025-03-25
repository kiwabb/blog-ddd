package com.jackmouse.system.blog.application.article.dto.query;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

/**
 * @ClassName ArticleIdQuery
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/14 10:53
 * @Version 1.0
 **/
@Getter
@Builder
@AllArgsConstructor
public class ArticleIdQuery {
    @NotNull
    private final UUID articleId;
}
