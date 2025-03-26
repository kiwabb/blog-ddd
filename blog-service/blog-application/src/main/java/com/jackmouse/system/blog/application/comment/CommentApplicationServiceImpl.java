package com.jackmouse.system.blog.application.comment;

import com.jackmouse.system.blog.application.comment.dto.create.CommentCreateCommand;
import com.jackmouse.system.blog.application.comment.dto.query.CommentResponse;
import com.jackmouse.system.blog.application.comment.dto.query.TargetIdQuery;
import com.jackmouse.system.blog.application.comment.port.input.service.CommentApplicationService;
import com.jackmouse.system.blog.domain.comment.entity.Comment;
import com.jackmouse.system.blog.domain.comment.repository.CommentRepository;
import com.jackmouse.system.blog.domain.comment.service.CommentPathGenerator;
import com.jackmouse.system.blog.domain.comment.valueobject.CommentId;
import com.jackmouse.system.blog.domain.exception.BlogDomainException;
import com.jackmouse.system.blog.domain.valueobject.Path;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @ClassName CommentApplicationServiceImpl
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/26 09:10
 * @Version 1.0
 **/
@Service
public class CommentApplicationServiceImpl implements CommentApplicationService {

    private final CommentRepository commentRepository;

    public CommentApplicationServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public List<CommentResponse> queryArticleComments(TargetIdQuery<UUID> commentQuery) {

        return List.of();
    }

    @Transactional
    @Override
    public void createComment(CommentCreateCommand commentCreateCommand) {
        Comment parentComment = null;
        if (commentCreateCommand.getParentCommentId() != null) {
            parentComment = commentRepository.findById(new CommentId(commentCreateCommand.getParentCommentId()))
                    .orElseThrow(() -> new BlogDomainException("父评论不存在"));
        }
        Comment comment = commentCreateCommand.toComment();
        comment.generatePath(parentComment);
        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(UUID commentId) {

    }
}
