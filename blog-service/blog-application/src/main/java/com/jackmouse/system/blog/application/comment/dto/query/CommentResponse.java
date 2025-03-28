package com.jackmouse.system.blog.application.comment.dto.query;

import com.jackmouse.system.blog.domain.comment.entity.Comment;
import com.jackmouse.system.blog.domain.valueobject.UserInfo;
import com.jackmouse.system.dto.query.UserInfoResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * @ClassName CommentResponse
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/25 14:05
 * @Version 1.0
 **/
@Getter
@Builder
@AllArgsConstructor
public class CommentResponse {
    private UUID id;
    private String content;
    private UserInfoResponse author;
    private ZonedDateTime createTime;
    private String path;
    private Integer depth;

    public static CommentResponse fromComment(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId().getValue())
                .content(comment.getContent().value())
                .author(new UserInfoResponse(comment.getUserId().getValue(),"kiwa", "kiwa", "Lv100", "公主"))
                .createTime(comment.getCreatedAt())
                .path(comment.getPath().getPath())
                .depth(comment.getDepth().value())
                .build();
    }
}
