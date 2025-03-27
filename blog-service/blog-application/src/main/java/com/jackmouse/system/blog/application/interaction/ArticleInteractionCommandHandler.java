package com.jackmouse.system.blog.application.interaction;

import com.jackmouse.system.blog.application.interaction.dto.InteractionCommand;
import com.jackmouse.system.blog.application.interaction.ports.input.service.BaseInteractionCommandHandler;
import com.jackmouse.system.blog.application.interaction.ports.output.message.publisher.InteractionModifyMessageEventPublisher;
import com.jackmouse.system.blog.domain.article.repository.ArticleRepository;
import com.jackmouse.system.blog.domain.article.valueobject.ArticleId;
import com.jackmouse.system.blog.domain.exception.ArticleNotFoundException;
import com.jackmouse.system.blog.domain.exception.BlogDomainException;
import com.jackmouse.system.blog.domain.interaction.cache.InteractionCacheService;
import com.jackmouse.system.blog.domain.interaction.entity.Favorite;
import com.jackmouse.system.blog.domain.interaction.entity.Like;
import com.jackmouse.system.blog.domain.interaction.event.ArticleInteractionModifyEvent;
import com.jackmouse.system.blog.domain.interaction.repository.InteractionRepository;
import com.jackmouse.system.blog.domain.interaction.valueobject.*;
import com.jackmouse.system.blog.domain.valueobject.UserId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

/**
 * @ClassName ArticleInteractionCommandHandler
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/21 10:06
 * @Version 1.0
 **/
@Slf4j
@Component
public class ArticleInteractionCommandHandler extends BaseInteractionCommandHandler {

    private final ArticleRepository articleRepository;
    private final InteractionCacheService interactionCacheService;

    public ArticleInteractionCommandHandler(ArticleRepository articleRepository,
                                            InteractionRepository interactionRepository,
                                            InteractionCacheService interactionCacheService,
                                            InteractionModifyMessageEventPublisher interactionModifyMessageEventPublisher) {
        super(interactionRepository, interactionModifyMessageEventPublisher);
        this.articleRepository = articleRepository;
        this.interactionCacheService = interactionCacheService;
    }


    @Transactional
    public void handleFavorite(InteractionCommand interactionCommand) {
        validateTargetExists(interactionCommand);
        Favorite favorite = interactionRepository
                .findFavoriteByArticleAndUserId(new FavoriteId(interactionCommand.getTargetId()),
                        new UserId(interactionCommand.getUserId()))
                .map(entity -> {
                    validateAndChangeFavoriteInteraction(entity, interactionCommand);
                    return entity;
                })
                .orElse(initFavoriteInteraction(interactionCommand));
        Favorite savedFavorite = interactionRepository.saveFavorite(favorite);
        interactionCacheService.updateFavoriteCount(favorite);
    }

    private Favorite initFavoriteInteraction(InteractionCommand interactionCommand) {
        return Favorite.builder()
                .targetId(new TargetId(interactionCommand.getTargetId()))
                .userId(new UserId(interactionCommand.getUserId()))
                .favoriteType(FavoriteType.ARTICLE)
                .interactionStatus(new InteractionStatus(interactionCommand.getIsActive(), OffsetDateTime.now()))
                .build();
    }

    private void validateAndChangeFavoriteInteraction(Favorite favorite, InteractionCommand interactionCommand) {
        if (favorite.getInteractionStatus().isActive() == interactionCommand.getIsActive()) {
            throw new BlogDomainException("请忽重复操作操作!");
        }
        favorite.changeInteractionStatus(interactionCommand.getIsActive());
    }


    @Override
    protected void validateTargetExists(InteractionCommand command) {
        if (!articleRepository.existById(new ArticleId(command.getTargetId()))) {
            throw new ArticleNotFoundException(command.getTargetId());
        }
    }

    @Override
    protected void updateLikeCount(Like like) {
        interactionCacheService.updateArticleLikeCount(like);
    }

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
                .targetType(TargetType.ARTICLE)
                .interactionStatus(new InteractionStatus(interactionCommand.getIsActive(), OffsetDateTime.now()))
                .build();
    }
}
