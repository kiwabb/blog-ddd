package com.jackmouse.system.blog.application.comment.dto.query;

import com.jackmouse.system.dto.query.UserInfoResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

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
    private UUID id;
    private String content;
    //private UserInfoResponse author;
    private ZonedDateTime createTime;
    private String path;
    private Integer depth;
    //private CommentResponse comment;
    private Integer likeCount;
    private Integer replyCount;
    private Long userId;
    private String username;
    private String avatar;
    private List<CommentNodeResponse> replies;
    //private CommentInteractionResponse interaction;
    private Boolean hasMoreReply;

}
