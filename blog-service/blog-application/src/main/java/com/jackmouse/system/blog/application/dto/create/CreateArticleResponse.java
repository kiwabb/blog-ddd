package com.jackmouse.system.blog.application.dto.create;

import com.jackmouse.system.blog.domain.article.valueobject.ArticleStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

/**
 * @ClassName CreateArticleResponse
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/14 11:03
 * @Version 1.0
 **/
@Getter
@Builder
@AllArgsConstructor
public class CreateArticleResponse {
    @NotNull
    private final UUID articleId;
    @NotNull
    private final ArticleStatus status;
    @NotNull
    private final String message;
}
