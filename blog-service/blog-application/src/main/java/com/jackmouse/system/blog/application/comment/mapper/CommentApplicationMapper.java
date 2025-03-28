package com.jackmouse.system.blog.application.comment.mapper;

import com.jackmouse.system.blog.application.comment.dto.query.CommentInteractionResponse;
import com.jackmouse.system.blog.application.comment.dto.query.CommentNodeResponse;
import com.jackmouse.system.blog.application.comment.dto.query.CommentResponse;
import com.jackmouse.system.blog.domain.comment.entity.Comment;
import com.jackmouse.system.blog.domain.interaction.valueobject.CommentInteraction;
import com.jackmouse.system.dto.query.UserInfoResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @ClassName CommentApplicationMapper
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/26 14:05
 * @Version 1.0
 **/
@Component
public class CommentApplicationMapper {
    public CommentNodeResponse toCommentNodeResponse(Comment secondLevelComment,
                                                     CommentInteraction commentInteraction) {
        int likeCount = 0;
        int replyCount = 0;
        if (commentInteraction != null) {
            likeCount = commentInteraction.likeCount();
            replyCount = commentInteraction.replyCount();
        }
        return CommentNodeResponse.builder()
                //.comment(CommentResponse.fromComment(secondLevelComment))
                .id(secondLevelComment.getId().getValue())
                .content(secondLevelComment.getContent().value())
                .createTime(secondLevelComment.getCreatedAt())
                .path(secondLevelComment.getPath().getPath())
                .depth(secondLevelComment.getDepth().value())
                .author(new UserInfoResponse(secondLevelComment.getUserId().getValue(),"kiwa", "kiwa", "Lv100", "公主"))
//                .userId(secondLevelComment.getUserId().getValue())
//                .username("")
//                .avatar("")
                .likeCount(likeCount)
                .replyCount(replyCount)
                .replies(new ArrayList<>())
//                .interaction(CommentInteractionResponse.builder()
//                        .likeCount(likeCount)
//                        .replyCount(replyCount)
//                        .build())
                .hasMoreReply(replyCount > 0)
                .build();
    }

    public CommentInteractionResponse toCommentInteractionResponse(CommentInteraction commentInteraction) {
        int likeCount = 0;
        int replyCount = 0;
        if (commentInteraction != null) {
            likeCount = commentInteraction.likeCount();
            replyCount = commentInteraction.replyCount();
        }
        return CommentInteractionResponse.builder()
                .likeCount(likeCount)
                .replyCount(replyCount)
                .build();
    }
}
