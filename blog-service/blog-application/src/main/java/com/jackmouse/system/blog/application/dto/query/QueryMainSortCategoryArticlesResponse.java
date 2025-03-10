package com.jackmouse.system.blog.application.dto.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * @ClassName QueryMainSortCategoryArticlesResponse
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/7 14:30
 * @Version 1.0
 **/
@Getter
@Builder
@AllArgsConstructor
public class QueryMainSortCategoryArticlesResponse {
    @Schema(
            description = "分类信息",
            implementation = CategoryResponse.class,
            example = "{\"id\": 1, \"name\": \"技术文章\"}"
    )
    private final CategoryResponse category;
    @Schema(
            description = "文章摘要列表（按发布时间倒序前6条）",
            maxLength = 6, // 修正为maxItems
            implementation = ArticleSummaryResponse.class
    )
    private final List<ArticleSummaryResponse> articles;
}
