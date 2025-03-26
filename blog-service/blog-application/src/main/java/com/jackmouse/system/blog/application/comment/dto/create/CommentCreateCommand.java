package com.jackmouse.system.blog.application.comment.dto.create;

import com.jackmouse.system.blog.domain.comment.entity.Comment;
import com.jackmouse.system.blog.domain.comment.valueobject.CommentId;
import com.jackmouse.system.blog.domain.comment.valueobject.CommentTargetType;
import com.jackmouse.system.blog.domain.interaction.valueobject.TargetId;
import com.jackmouse.system.blog.domain.valueobject.Content;
import com.jackmouse.system.blog.domain.valueobject.Path;
import com.jackmouse.system.blog.domain.valueobject.UserId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

/**
 * @ClassName CommentCreateCommand
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/25 14:10
 * @Version 1.0
 **/
@Getter
@Builder
@AllArgsConstructor
public class CommentCreateCommand {
    private UUID targetId;
    private Long userId;
    private String targetType;
    private UUID parentCommentId;
    private String content;

    public Comment toComment() {
        return Comment.builder()
                .targetId(new TargetId(targetId))
                .userId(new UserId(userId))
                .targetType(CommentTargetType.valueOf(targetType))
                .parentCommentId(parentCommentId == null ? null : new CommentId(parentCommentId))
                .content(new Content(content))
                .build();
    }
}
