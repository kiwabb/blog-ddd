package com.jackmouse.system.blog.domain.comment.repository;

import com.jackmouse.system.blog.domain.comment.entity.Comment;
import com.jackmouse.system.blog.domain.comment.valueobject.CommentId;

import java.util.Optional;

/**
 * @ClassName CommentRepository
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/26 09:11
 * @Version 1.0
 **/
public interface CommentRepository {
    void save(Comment comment);

    Optional<Comment> findById(CommentId commentId);
}
