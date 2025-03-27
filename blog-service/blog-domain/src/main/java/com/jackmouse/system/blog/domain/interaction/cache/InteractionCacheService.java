package com.jackmouse.system.blog.domain.interaction.cache;

import com.jackmouse.system.blog.domain.comment.entity.Comment;
import com.jackmouse.system.blog.domain.comment.valueobject.CommentId;
import com.jackmouse.system.blog.domain.interaction.entity.Favorite;
import com.jackmouse.system.blog.domain.interaction.entity.Like;
import com.jackmouse.system.blog.domain.interaction.valueobject.CommentInteraction;
import com.jackmouse.system.blog.domain.interaction.valueobject.TargetId;

import java.util.Map;
import java.util.Set;

/**
 * @ClassName InteractionCacheService
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/21 10:53
 * @Version 1.0
 **/
public interface InteractionCacheService {
    void updateArticleLikeCount(Like like);

    void updateFavoriteCount(Favorite favorite);

    Map<CommentId, CommentInteraction> batchGetCommentInteractions(Set<CommentId> rootIds);

    void addReplyCount(Comment comment);

    void subReplyCount(Comment comment);

    void updateCommentLikeCount(Like like);
}
