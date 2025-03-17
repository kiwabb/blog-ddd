package com.jackmouse.system.blog.interfaces.rest;

import com.jackmouse.system.blog.application.dto.create.CreateArticleCommand;
import com.jackmouse.system.blog.application.dto.create.CreateArticleResponse;
import com.jackmouse.system.blog.application.dto.query.ArticleIdQuery;
import com.jackmouse.system.blog.application.dto.query.ArticleResponse;
import com.jackmouse.system.blog.application.dto.query.QueryMainSortCategoryArticlesResponse;
import com.jackmouse.system.blog.application.ports.input.service.ArticleApplicationService;
import com.jackmouse.system.response.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    private final ArticleApplicationService articleApplicationService;

    public ArticleController(ArticleApplicationService articleApplicationService) {
        this.articleApplicationService = articleApplicationService;
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
        return Result.succeed(articleApplicationService.queryMainSortCategoryArticles());
    }

    @Operation(
            summary = "获取文章详情",
            description = "根据文章ID查询文章详情",
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
                                    name = "ArticleDetailExample",
                                    value = """
                {
                  "code": 200,
                  "message": "操作成功",
                  "data": {
                    "id": "550e8400-e29b-41d4-a716-446655440000",
                    "title": "深入理解DDD架构",
                    "content": "领域驱动设计（DDD）是一种软件设计方法论...",
                    "status": "PUBLISHED",
                    "category": {
                      "id": 1,
                      "name": "技术文章"
                    },
                    "tags": [
                      {"name": "架构设计"},
                      {"name": "DDD"}
                    ],
                    "author": {
                      "id": "user-123",
                      "name": "张三",
                      "avatar": "/avatars/user123.jpg"
                    },
                    "stats": {
                      "likeCount": 256,
                      "favoriteCount": 89,
                      "commentCount": 34,
                      "readCount": 1024,
                      "hotScore": 8.5
                    },
                    "publishTime": "2025-03-07T10:00:00",
                    "lastModifiedTime": "2025-03-07T11:30:00",
                    "attachments": [
                      {
                        "name": "架构图.png",
                        "url": "/attachments/arch.png",
                        "type": "IMAGE"
                      }
                    ]
                  }
                }
                """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "文章不存在",
                    content = @Content(
                            schema = @Schema(implementation = Result.class),
                            examples = @ExampleObject(
                                    value = """
                {
                  "code": 404,
                  "message": "指定文章不存在"
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
    @GetMapping("/{id}")
    public Result<ArticleResponse> queryArticleById(@PathVariable("id") UUID id) {
        return Result.succeed(articleApplicationService.queryArticleById(ArticleIdQuery.builder().articleId(id).build()));
    }

    @Operation(
            summary = "创建文章",
            description = "创建一篇新的文章",
            method = "POST"
    )
    @PostMapping()
    public Result<CreateArticleResponse> createArticle(@Valid @RequestBody CreateArticleCommand createArticleCommand) {
        return Result.succeed(articleApplicationService.createArticle(createArticleCommand));
    }
}
