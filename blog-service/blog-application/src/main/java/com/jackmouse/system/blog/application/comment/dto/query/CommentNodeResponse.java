package com.jackmouse.system.blog.application.comment.dto.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * @ClassName CommentNodeResponse
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/26 13:17
 * @Version 1.0
 **/
@Getter
@Builder
@AllArgsConstructor
public class CommentNodeResponse {
    private CommentResponse comment;
    private List<CommentNodeResponse> replies;
    private CommentInteractionResponse interaction;
    private Boolean hasMoreReply;
}
