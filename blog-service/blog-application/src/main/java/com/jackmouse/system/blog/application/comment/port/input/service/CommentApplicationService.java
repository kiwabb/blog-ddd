package com.jackmouse.system.blog.application.comment.port.input.service;

import com.jackmouse.system.blog.application.comment.dto.create.CommentCreateCommand;
import com.jackmouse.system.blog.application.comment.dto.query.CommentNodeResponse;
import com.jackmouse.system.blog.application.comment.dto.query.CommentResponse;
import com.jackmouse.system.blog.application.comment.dto.query.TargetIdQuery;
import com.jackmouse.system.response.PageResult;

import java.util.List;
import java.util.UUID;

/**
 * @ClassName CommentApplicationService
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/25 14:07
 * @Version 1.0
 **/
public interface CommentApplicationService {
    PageResult<CommentNodeResponse> queryArticleComments(TargetIdQuery commentQuery);

    void createComment(CommentCreateCommand commentCreateCommand);

    void deleteComment(UUID commentId);
}
