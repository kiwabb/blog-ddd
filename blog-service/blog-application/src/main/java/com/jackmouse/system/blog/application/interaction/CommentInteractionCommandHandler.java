package com.jackmouse.system.blog.application.interaction;

import com.jackmouse.system.blog.application.interaction.dto.InteractionCommand;
import com.jackmouse.system.blog.application.interaction.ports.input.service.BaseInteractionCommandHandler;
import com.jackmouse.system.blog.application.interaction.ports.output.message.publisher.InteractionModifyMessageEventPublisher;
import com.jackmouse.system.blog.domain.comment.repository.CommentRepository;
import com.jackmouse.system.blog.domain.comment.valueobject.CommentId;
import com.jackmouse.system.blog.domain.exception.ArticleNotFoundException;
import com.jackmouse.system.blog.domain.interaction.cache.InteractionCacheService;
import com.jackmouse.system.blog.domain.interaction.entity.Like;
import com.jackmouse.system.blog.domain.interaction.repository.InteractionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @ClassName ArticleInteractionCommandHandler
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/21 10:06
 * @Version 1.0
 **/
@Slf4j
@Component
public class CommentInteractionCommandHandler extends BaseInteractionCommandHandler {

    private final InteractionCacheService interactionCacheService;
    private final CommentRepository commentRepository;

    public CommentInteractionCommandHandler(CommentRepository commentRepository,
                                            InteractionRepository interactionRepository,
                                            InteractionModifyMessageEventPublisher interactionModifyMessageEventPublisher,
                                            InteractionCacheService interactionCacheService) {
        super(interactionRepository, interactionModifyMessageEventPublisher);
        this.commentRepository = commentRepository;
        this.interactionCacheService = interactionCacheService;
    }



    @Override
    protected void validateTargetExists(InteractionCommand interactionCommand) {
        if (!commentRepository.existById(new CommentId(interactionCommand.getTargetId()))) {
            throw new ArticleNotFoundException(interactionCommand.getTargetId());
        }
    }

    @Override
    protected void updateLikeCount(Like like) {
        interactionCacheService.updateCommentLikeCount(like);
    }
}
