package com.jackmouse.system.blog.interfaces.rest;

import com.jackmouse.system.blog.application.interaction.dto.InteractionCommand;
import com.jackmouse.system.blog.application.interaction.ports.input.service.ArticleInteractionApplicationService;
import com.jackmouse.system.response.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName ArticleInteractionController
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/21 09:10
 * @Version 1.0
 **/
@Tag(name = "文章互动接口", description = "提供点赞/收藏等互动功能")
@RestController
@RequestMapping("/article/interaction")
public class ArticleInteractionController {

    private final ArticleInteractionApplicationService articleInteractionApplicationService;

    public ArticleInteractionController(ArticleInteractionApplicationService articleInteractionApplicationService) {
        this.articleInteractionApplicationService = articleInteractionApplicationService;
    }

    @Operation(summary = "点赞文章", description = "用户对文章进行点赞/取消点赞")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "操作成功",
                    content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(responseCode = "400", description = "非法请求",
                    content = @Content(examples = @ExampleObject(value = "{code:400,message:'参数错误'}")))
    })
    @PostMapping("/like")
    public Result<Void> likeArticle(@RequestBody InteractionCommand interactionCommand) {
        articleInteractionApplicationService.handleLike(interactionCommand);
        return Result.succeed(null);
    }

    @Operation(summary = "收藏文章", description = "用户收藏/取消收藏文章")
    @PostMapping("/favorite")
    public Result<Void> favoriteArticle(@RequestBody InteractionCommand interactionCommand) {
        articleInteractionApplicationService.handleFavorite(interactionCommand);
        return Result.succeed(null);
    }
}
