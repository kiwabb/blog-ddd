package com.jackmouse.system.blog.dataaccess.comment.adapter.repository;

import com.jackmouse.system.blog.dataaccess.comment.entity.CommentEntity;
import com.jackmouse.system.blog.dataaccess.comment.repository.CommentJpaRepository;
import com.jackmouse.system.blog.domain.comment.entity.Comment;
import com.jackmouse.system.blog.domain.comment.repository.CommentRepository;
import com.jackmouse.system.blog.domain.comment.valueobject.CommentId;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @ClassName CommentRepositoryImpl
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/26 10:08
 * @Version 1.0
 **/
@Component
public class CommentRepositoryImpl implements CommentRepository {

    private final CommentJpaRepository commentJpaRepository;

    public CommentRepositoryImpl(CommentJpaRepository commentJpaRepository) {
        this.commentJpaRepository = commentJpaRepository;
    }

    @Override
    public void save(Comment comment) {
        commentJpaRepository.save(CommentEntity.from(comment));
    }

    @Override
    public Optional<Comment> findById(CommentId commentId) {
        return commentJpaRepository.findById(commentId.getValue())
                .map(CommentEntity::toComment);
    }
}
