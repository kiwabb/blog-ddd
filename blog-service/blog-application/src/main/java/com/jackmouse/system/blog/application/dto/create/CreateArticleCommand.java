package com.jackmouse.system.blog.application.dto.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * @ClassName CreateArticleCommand
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/14 11:05
 * @Version 1.0
 **/
@Getter
@Builder
@AllArgsConstructor
public class CreateArticleCommand {
    private final String title;
    private final String content;
    private final Long categoryId;
    private final List<Long> tagIds;
    private final String cover;
    private final boolean isDraft;
}
