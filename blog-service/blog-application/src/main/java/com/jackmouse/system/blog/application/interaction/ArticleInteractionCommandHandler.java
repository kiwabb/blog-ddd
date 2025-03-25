package com.jackmouse.system.blog.application.interaction;

import com.jackmouse.system.blog.application.interaction.dto.InteractionCommand;
import com.jackmouse.system.blog.application.interaction.ports.output.message.publisher.InteractionModifyMessageEventPublisher;
import com.jackmouse.system.blog.domain.article.entity.Article;
import com.jackmouse.system.blog.domain.article.repository.ArticleRepository;
import com.jackmouse.system.blog.domain.article.valueobject.ArticleId;
import com.jackmouse.system.blog.domain.exception.BlogDomainException;
import com.jackmouse.system.blog.domain.exception.BlogNotFoundException;
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
public class ArticleInteractionCommandHandler {

    private final ArticleRepository articleRepository;
    private final InteractionRepository interactionRepository;
    private final InteractionCacheService interactionCacheService;
    private final InteractionModifyMessageEventPublisher interactionModifyMessageEventPublisher;

    public ArticleInteractionCommandHandler(ArticleRepository articleRepository,
                                            InteractionRepository interactionRepository,
                                            InteractionCacheService interactionCacheService,
                                            InteractionModifyMessageEventPublisher interactionModifyMessageEventPublisher) {
        this.articleRepository = articleRepository;
        this.interactionRepository = interactionRepository;
        this.interactionCacheService = interactionCacheService;
        this.interactionModifyMessageEventPublisher = interactionModifyMessageEventPublisher;
    }

    @Transactional
    public void handleLike(InteractionCommand interactionCommand) {
        validateArticleExist(interactionCommand);
        Like like = interactionRepository
                .findLikeByArticleAndUserId(new ArticleId(interactionCommand.getTargetId()),
                        new UserId(interactionCommand.getUserId()))
                .map(entity -> {
                    validateAndChangeLikeInteraction(entity, interactionCommand);
                    return entity;
                })
                .orElse(initLikeInteraction(interactionCommand));
        Like savedLike = interactionRepository.saveLike(like);
        interactionCacheService.updateLikeCount(like);
        interactionModifyMessageEventPublisher.publish(
                new ArticleInteractionModifyEvent(savedLike, ZonedDateTime.now()));
    }

    @Transactional
    public void handleFavorite(InteractionCommand interactionCommand) {
        validateArticleExist(interactionCommand);
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

    private void validateArticleExist(InteractionCommand interactionCommand) {
        Article articleRepositoryById = articleRepository.findById(new ArticleId(interactionCommand.getTargetId()));
        if (articleRepositoryById == null) {
            throw new BlogNotFoundException("文章不存在!");
        }
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
