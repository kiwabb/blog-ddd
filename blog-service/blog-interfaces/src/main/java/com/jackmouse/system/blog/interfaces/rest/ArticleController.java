package com.jackmouse.system.blog.interfaces.rest;

import com.jackmouse.system.blog.application.dto.query.QueryMainSortCategoryArticlesResponse;
import com.jackmouse.system.blog.application.ports.input.service.ArticleQueryApplicationService;
import com.jackmouse.system.blog.response.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName ArticleController
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/7 14:18
 * @Version 1.0
 **/
@Tag(name = "文章管理接口", description = "提供文章相关的所有操作接口")
@Slf4j
@RestController
@RequestMapping( value = "/article", produces = "application/json")
public class ArticleController {
    private final ArticleQueryApplicationService articleQueryApplicationService;

    public ArticleController(ArticleQueryApplicationService articleQueryApplicationService) {
        this.articleQueryApplicationService = articleQueryApplicationService;
    }

    @Operation(
            summary = "获取分类排序文章列表",
            description = "查询首页需要展示的分类及其对应文章列表",
            method = "GET"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "查询成功",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Result.class),
                            examples = @ExampleObject(
                                    name = "SuccessExample",
                                    value = """
                    {
                      "code": 200,
                      "message": "操作成功",
                      "data": [
                        {
                          "category": {
                            "id": 1,
                            "name": "技术文章",
                            "icon": "/icons/tech.png"
                          },
                          "articles": [
                            {
                              "id": "550e8400-e29b-41d4-a716-446655440000",
                              "title": "Spring Boot教程",
                              "summary": "本文详细讲解Spring Boot核心特性...",
                              "cover": "/covers/spring.jpg",
                              "author": "张三",
                              "publishTime": "2025-03-07T10:00:00",
                              "hotScore": 8.5,
                              "likeCount": 256,
                              "favoriteCount": 89,
                              "commentCount": 34,
                              "readCount": 1024,
                              "tags": [
                                {"name": "后端开发"},
                                {"name": "云原生"}
                              ]
                            }
                          ]
                        }
                      ]
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "服务器内部错误",
                    content = @Content(
                            schema = @Schema(implementation = Result.class),
                            examples = @ExampleObject(
                                    value = """
                        {
                          "code": 500,
                          "message": "系统繁忙，请稍后再试"
                        }
                        """
                            )
                    )
            )
    })
    @GetMapping("/main")
    public Result<List<QueryMainSortCategoryArticlesResponse>> queryMainSortCategoryArticles() {
        return Result.succeed(articleQueryApplicationService.queryMainSortCategoryArticles());
    }
}
