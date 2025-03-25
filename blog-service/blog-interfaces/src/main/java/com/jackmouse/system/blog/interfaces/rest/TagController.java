package com.jackmouse.system.blog.interfaces.rest;

import com.jackmouse.system.blog.application.article.dto.query.TagResponse;
import com.jackmouse.system.blog.application.article.ports.input.service.ArticleApplicationService;
import com.jackmouse.system.response.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

/**
 * @ClassName TagController
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/25 13:19
 * @Version 1.0
 **/
@Tag(name = "标签", description = "标签相关接口")
@RestController
@RequestMapping( value = "/tag", produces = "application/json")
public class TagController {
    private final ArticleApplicationService articleApplicationService;

    public TagController(ArticleApplicationService articleApplicationService) {
        this.articleApplicationService = articleApplicationService;
    }

    @Operation(
            summary = "获取标签列表",
            description = "查询所有标签",
            method = "GET"
    )
    @RequestMapping()
    public Result<List<TagResponse>> queryTags() {
        return Result.of(articleApplicationService.queryTags());
    }
}
