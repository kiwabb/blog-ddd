package com.jackmouse.system.blog.domain.comment.repository;

import com.jackmouse.system.blog.domain.comment.entity.Comment;
import com.jackmouse.system.blog.domain.comment.specification.query.CommentPageQuerySpec;
import com.jackmouse.system.blog.domain.comment.valueobject.CommentId;
import com.jackmouse.system.blog.domain.valueobject.PageResult;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * @ClassName CommentRepository
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/26 09:11
 * @Version 1.0
 **/
public interface CommentRepository {
    Comment save(Comment comment);

    Optional<Comment> findById(CommentId commentId);

    PageResult<Comment> findByTargetId(CommentPageQuerySpec commentQuerySpec);

    Map<CommentId, List<Comment>> findSecondLevelComments(Set<CommentId> rootIds, int previewReplyCount);

    boolean existById(CommentId commentId);

    void deleteComment(Comment comment);
}
