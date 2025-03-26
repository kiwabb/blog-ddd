package com.jackmouse.system.blog.interfaces.rest;

import com.jackmouse.system.blog.application.comment.dto.create.CommentCreateCommand;
import com.jackmouse.system.blog.application.comment.dto.query.CommentNodeResponse;
import com.jackmouse.system.blog.application.comment.dto.query.CommentResponse;
import com.jackmouse.system.blog.application.comment.dto.query.TargetIdQuery;
import com.jackmouse.system.blog.application.comment.port.input.service.CommentApplicationService;
import com.jackmouse.system.blog.domain.interaction.valueobject.TargetId;
import com.jackmouse.system.response.PageResult;
import com.jackmouse.system.response.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * @ClassName CommentController
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/26 10:21
 * @Version 1.0
 **/
@Tag(name = "评论", description = "评论相关接口")
@RestController
@RequestMapping( value = "/comment", produces = "application/json")
public class CommentController {

    private final CommentApplicationService commentApplicationService;

    public CommentController(CommentApplicationService commentApplicationService) {
        this.commentApplicationService = commentApplicationService;
    }

    @Operation(
            summary = "创建评论",
            description = "创建评论",
            method = "POST"
    )
    @PostMapping("")
    public Result<Void> createComment(@RequestBody CommentCreateCommand commentCreateCommand) {
        commentApplicationService.createComment(commentCreateCommand);
        return Result.succeed(null);
    }

    @Operation(
            summary = "删除评论",
            description = "删除评论",
            method = "DELETE"
    )
    @DeleteMapping("/{commentId}")
    public Result<Void> deleteComment(@PathVariable("commentId") UUID commentId) {
        commentApplicationService.deleteComment(commentId);
        return Result.succeed(null);
    }

    @Operation(
            summary = "查询文章评论",
            description = "查询文章评论",
            method = "GET"
    )
    @GetMapping("")
    public PageResult<CommentNodeResponse> queryArticleComments(TargetIdQuery idQuery) {
        return commentApplicationService.queryArticleComments(idQuery);
    }
}
