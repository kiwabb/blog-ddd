package com.jackmouse.system.blog.application.comment.dto.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * @ClassName CommentInteractionResponse
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/26 13:20
 * @Version 1.0
 **/
@Getter
@Builder
@AllArgsConstructor
public class CommentInteractionResponse {
    private Integer likeCount;
    private Integer replyCount;
}
