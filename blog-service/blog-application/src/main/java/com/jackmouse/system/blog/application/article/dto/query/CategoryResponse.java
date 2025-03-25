package com.jackmouse.system.blog.application.article.dto.query;

import com.jackmouse.system.blog.domain.article.entity.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

/**
 * @ClassName Category
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/7 14:32
 * @Version 1.0
 **/
@Getter
@Builder
@AllArgsConstructor
public class CategoryResponse {
    @Schema(description = "分类ID", example = "1")
    private final Long categoryId;
    @Schema(description = "分类名称", example = "技术文章")
    private final String categoryName;
    @Schema(description = "排序" , example = "1")
    private final Integer sort;

    public static List<CategoryResponse> fromCategoryList(List<Category> categories) {
        return categories.stream().map(CategoryResponse::fromCategory).toList();
    }

    public static CategoryResponse fromCategory(Category category) {
        return CategoryResponse.builder()
                .categoryId(category.getId().getValue())
                .categoryName(category.getName().value())
                .sort(category.getSort().value())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryResponse that = (CategoryResponse) o;
        return Objects.equals(categoryId, that.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(categoryId);
    }
}
