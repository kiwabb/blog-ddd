package com.jackmouse.system.blog.interfaces.rest;

import com.jackmouse.system.blog.application.article.dto.query.CategoryResponse;
import com.jackmouse.system.blog.application.article.ports.input.service.ArticleApplicationService;
import com.jackmouse.system.response.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName CategoryController
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/25 13:19
 * @Version 1.0
 **/
@Tag(name = "分类", description = "分类相关接口")
@RestController
@RequestMapping( value = "/category", produces = "application/json")
public class CategoryController {
    private final ArticleApplicationService articleApplicationService;

    public CategoryController(ArticleApplicationService articleApplicationService) {
        this.articleApplicationService = articleApplicationService;
    }

    @Operation(
            summary = "获取分类列表",
            description = "查询所有分类",
            method = "GET"
    )
    @GetMapping
    public Result<List<CategoryResponse>> queryCategories() {
        return Result.of(articleApplicationService.queryCategories());
    }
}
