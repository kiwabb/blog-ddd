package com.jackmouse.system.blog.dataaccess.comment.adapter.repository;

import com.jackmouse.system.blog.dataaccess.comment.entity.CommentEntity;
import com.jackmouse.system.blog.dataaccess.comment.repository.CommentJpaRepository;
import com.jackmouse.system.blog.domain.comment.entity.Comment;
import com.jackmouse.system.blog.domain.comment.repository.CommentRepository;
import com.jackmouse.system.blog.domain.comment.specification.query.CommentPageQuerySpec;
import com.jackmouse.system.blog.domain.comment.valueobject.CommentId;
import com.jackmouse.system.blog.domain.valueobject.PageResult;
import com.jackmouse.system.utils.RepositoryUtil;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

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
    public Comment save(Comment comment) {
        return commentJpaRepository.save(CommentEntity.from(comment)).toComment();
    }

    @Override
    public Optional<Comment> findById(CommentId commentId) {
        return commentJpaRepository.findById(commentId.getValue())
                .map(CommentEntity::toComment);
    }

    @Override
    public PageResult<Comment> findByTargetId(CommentPageQuerySpec query) {

        Pageable pageable = RepositoryUtil.toPageable(query);
        Specification<CommentEntity> specification = (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("targetId"), query.getTargetId().value()));
            predicates.add(cb.equal(root.get("depth"), query.getDepth().value()));
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        Page<CommentEntity> commentEntityPage = commentJpaRepository.findAll(specification, pageable);

        return RepositoryUtil.toPageData(commentEntityPage);
    }

    @Override
    public Map<CommentId, List<Comment>> findSecondLevelComments(Set<CommentId> rootIds, int previewReplyCount) {

        List<CommentEntity> commentEntities = commentJpaRepository.findLimitedByParentIds(
                rootIds.stream().map(CommentId::getValue).collect(Collectors.toList()), previewReplyCount);
        return commentEntities.stream()
                .collect(Collectors.groupingBy(
                        entity -> new CommentId(entity.getParentCommentId()),
                        Collectors.mapping(CommentEntity::toComment, Collectors.toList())
                ));
    }

    @Override
    public boolean existById(CommentId commentId) {
        return commentJpaRepository.existsById(commentId.getValue());
    }

    @Override
    public void deleteComment(Comment comment) {
        commentJpaRepository.findById(comment.getId().getValue()).ifPresent(commentEntity -> {
            String path = commentEntity.getPath() + "." + commentEntity.getId();
            commentJpaRepository.deleteAllChildren(path);
        });
    }
}
