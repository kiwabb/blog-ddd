package com.jackmouse.system.blog.application.comment;

import com.jackmouse.system.blog.application.comment.dto.create.CommentCreateCommand;
import com.jackmouse.system.blog.application.comment.dto.query.CommentInteractionResponse;
import com.jackmouse.system.blog.application.comment.dto.query.CommentNodeResponse;
import com.jackmouse.system.blog.application.comment.dto.query.CommentResponse;
import com.jackmouse.system.blog.application.comment.dto.query.TargetIdQuery;
import com.jackmouse.system.blog.application.comment.mapper.CommentApplicationMapper;
import com.jackmouse.system.blog.application.comment.port.input.service.CommentApplicationService;
import com.jackmouse.system.blog.domain.article.repository.ArticleRepository;
import com.jackmouse.system.blog.domain.article.valueobject.ArticleId;
import com.jackmouse.system.blog.domain.comment.entity.Comment;
import com.jackmouse.system.blog.domain.comment.repository.CommentRepository;
import com.jackmouse.system.blog.domain.comment.valueobject.CommentId;
import com.jackmouse.system.blog.domain.comment.valueobject.CommentTargetType;
import com.jackmouse.system.blog.domain.exception.ArticleNotFoundException;
import com.jackmouse.system.blog.domain.exception.BlogDomainException;
import com.jackmouse.system.blog.domain.exception.BlogNotFoundException;
import com.jackmouse.system.blog.domain.exception.CommentNotFoundException;
import com.jackmouse.system.blog.domain.interaction.cache.InteractionCacheService;
import com.jackmouse.system.blog.domain.interaction.valueobject.CommentInteraction;
import com.jackmouse.system.blog.domain.valueobject.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName CommentApplicationServiceImpl
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/26 09:10
 * @Version 1.0
 **/
@Service
public class CommentApplicationServiceImpl implements CommentApplicationService {

    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final InteractionCacheService interactionCacheService;
    private final CommentApplicationMapper commentApplicationMapper;

    public CommentApplicationServiceImpl(ArticleRepository articleRepository, CommentRepository commentRepository,
                                         InteractionCacheService interactionCacheService, CommentApplicationMapper commentApplicationMapper) {
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;
        this.interactionCacheService = interactionCacheService;
        this.commentApplicationMapper = commentApplicationMapper;
    }

    @Override
    public com.jackmouse.system.response.PageResult<CommentNodeResponse> queryArticleComments(TargetIdQuery commentQuery) {
        validateArticleExist(commentQuery.getTargetId());
        PageResult<Comment> rootComments = commentRepository.findByTargetId(commentQuery.toCommentQuerySpec());

        // 批量预加载二级回复
        Set<CommentId> commentIds = extractRootIds(rootComments.data());
        Map<CommentId, List<Comment>> secondLevelMap = commentRepository
                .findSecondLevelComments(commentIds, commentQuery.getPreviewReplyCount());
        // 所有需要返回的评论ID
        commentIds.addAll(extractSecondLevelIds(secondLevelMap));
        // 聚合互动数据
        Map<CommentId, CommentInteraction> commentInteractions = interactionCacheService
                .batchGetCommentInteractions(commentIds);
        return new com.jackmouse.system.response.PageResult<>(rootComments.total(), rootComments.currentPage(),
                assembleCommentNodeResponses(rootComments.data(), secondLevelMap, commentInteractions));
    }



    @Transactional
    @Override
    public void createComment(CommentCreateCommand commentCreateCommand) {
        Comment comment = commentCreateCommand.toComment();
        validateExist(commentCreateCommand, comment);
        Comment parentComment = null;
        if (commentCreateCommand.getParentCommentId() != null) {
            parentComment = commentRepository.findById(new CommentId(commentCreateCommand.getParentCommentId()))
                    .orElseThrow(() -> new CommentNotFoundException(commentCreateCommand.getParentCommentId()));
        }
        comment.generatePath(parentComment);
        Comment savedComment = commentRepository.save(comment);
        interactionCacheService.addReplyCount(savedComment);
    }

    private void validateExist(CommentCreateCommand commentCreateCommand, Comment comment) {
        if (Objects.equals(comment.getTargetType(), CommentTargetType.ARTICLE)) {
            validateArticleExist(commentCreateCommand.getTargetId());
        }
    }

    private void validateArticleExist(UUID id) {
        if (!articleRepository.existById(new ArticleId(id))) {
            throw new ArticleNotFoundException(id);
        }
    }



    @Transactional
    @Override
    public void deleteComment(UUID commentId) {
        Comment comment = commentRepository.findById(new CommentId(commentId))
                .orElseThrow(() -> new CommentNotFoundException(commentId));
        // TODO 权限校验
        commentRepository.deleteComment(comment);
        interactionCacheService.subReplyCount(comment);
    }

    private List<CommentNodeResponse> assembleCommentNodeResponses(List<Comment> rootComments,
                                                                   Map<CommentId, List<Comment>> secondLevelMap,
                                                                   Map<CommentId, CommentInteraction> commentInteractions
    ) {
        return rootComments.stream()
                .map(rootComment -> {
                    CommentInteractionResponse commentInteractionResponse = commentApplicationMapper
                            .toCommentInteractionResponse(commentInteractions.get(rootComment.getId()));
                    List<Comment> secondLevelMapOrDefault = secondLevelMap
                            .getOrDefault(rootComment.getId(), Collections.emptyList());
                    return CommentNodeResponse.builder()
                            //.comment(CommentResponse.fromComment(rootComment))
                            .id(rootComment.getId().getValue())
                            .content(rootComment.getContent().value())
                            .createTime(rootComment.getCreatedAt())
                            .path(rootComment.getPath().getPath())
                            .depth(rootComment.getDepth().value())
                            .userId(rootComment.getUserId().getValue())
                            .username("kiwa")
                            .avatar("kiwa")
                            .likeCount(commentInteractionResponse.getLikeCount())
                            .replyCount(commentInteractionResponse.getReplyCount())
                            .replies(secondLevelMapOrDefault
                                    .stream()
                                    .map(secondLevelComment ->
                                            commentApplicationMapper.toCommentNodeResponse(secondLevelComment, commentInteractions.get(secondLevelComment.getId())))
                                    .collect(Collectors.toList())
                            )
                            //.interaction(commentInteractionResponse)
                            .hasMoreReply(commentInteractionResponse.getReplyCount() > secondLevelMapOrDefault.size())
                            .build();
                }).collect(Collectors.toList());
    }

    private Collection<? extends CommentId> extractSecondLevelIds(Map<CommentId, List<Comment>> secondLevelMap) {
        return secondLevelMap.values()
                .stream()
                .flatMap(Collection::stream)
                .map(Comment::getId)
                .collect(Collectors.toSet());
    }

    private Set<CommentId> extractRootIds(List<Comment> rootComments) {
        return rootComments.stream().map(Comment::getId).collect(Collectors.toSet());
    }
}
