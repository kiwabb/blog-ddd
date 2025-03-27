package com.jackmouse.system.blog.application.interaction.ports.input.service;

import com.jackmouse.system.blog.application.interaction.dto.InteractionCommand;
import com.jackmouse.system.blog.application.interaction.ports.output.message.publisher.InteractionModifyMessageEventPublisher;
import com.jackmouse.system.blog.domain.exception.BlogDomainException;
import com.jackmouse.system.blog.domain.interaction.entity.Like;
import com.jackmouse.system.blog.domain.interaction.event.ArticleInteractionModifyEvent;
import com.jackmouse.system.blog.domain.interaction.repository.InteractionRepository;
import com.jackmouse.system.blog.domain.interaction.valueobject.InteractionStatus;
import com.jackmouse.system.blog.domain.interaction.valueobject.TargetId;
import com.jackmouse.system.blog.domain.valueobject.UserId;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

/**
 * @ClassName BaseInteractionCommandHandler
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/27 11:16
 * @Version 1.0
 **/
public abstract class BaseInteractionCommandHandler {

    protected final InteractionRepository interactionRepository;
    private final InteractionModifyMessageEventPublisher interactionModifyMessageEventPublisher;

    protected BaseInteractionCommandHandler(InteractionRepository interactionRepository, InteractionModifyMessageEventPublisher interactionModifyMessageEventPublisher) {
        this.interactionRepository = interactionRepository;
        this.interactionModifyMessageEventPublisher = interactionModifyMessageEventPublisher;
    }

    public void handleLike(InteractionCommand interactionCommand) {
        validateTargetExists(interactionCommand);
        Like like = interactionRepository
                .findLikeByTargetIdAndUserId(new TargetId(interactionCommand.getTargetId()),
                        new UserId(interactionCommand.getUserId()))
                .map(entity -> {
                    validateAndChangeLikeInteraction(entity, interactionCommand);
                    return entity;
                })
                .orElse(initLikeInteraction(interactionCommand));
        Like savedLike = interactionRepository.saveLike(like);
        updateLikeCount(like);
        interactionModifyMessageEventPublisher.publish(
                new ArticleInteractionModifyEvent(savedLike, ZonedDateTime.now()));
    }

    protected abstract void validateTargetExists(InteractionCommand command);
    protected abstract void updateLikeCount(Like like);

    private void validateAndChangeLikeInteraction(Like like, InteractionCommand interactionCommand) {
        if (like.getInteractionStatus().isActive() == interactionCommand.getIsActive()) {
            throw new BlogDomainException("请忽重复操作操作!");
        }
        like.changeInteractionStatus(interactionCommand.getIsActive());
    }
    private Like initLikeInteraction(InteractionCommand interactionCommand) {
        return  Like.builder()
                .targetId(new TargetId(interactionCommand.getTargetId()))
                .userId(new UserId(interactionCommand.getUserId()))
                .targetType(interactionCommand.getTargetType())
                .interactionStatus(new InteractionStatus(interactionCommand.getIsActive(), OffsetDateTime.now()))
                .build();
    }
}
