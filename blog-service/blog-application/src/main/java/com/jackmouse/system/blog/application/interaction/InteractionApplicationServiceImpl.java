package com.jackmouse.system.blog.application.interaction;

import com.jackmouse.system.blog.application.interaction.dto.InteractionCommand;
import com.jackmouse.system.blog.application.interaction.ports.input.service.InteractionApplicationService;
import com.jackmouse.system.blog.domain.exception.BlogDomainException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName ArticleInteractionApplicationServiceImpl
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/21 09:58
 * @Version 1.0
 **/
@Service
public class InteractionApplicationServiceImpl implements InteractionApplicationService {
    private final ArticleInteractionCommandHandler articleInteractionCommandHandler;
    private final CommentInteractionCommandHandler commentInteractionCommandHandler;

    public InteractionApplicationServiceImpl(ArticleInteractionCommandHandler articleInteractionCommandHandler, CommentInteractionCommandHandler commentInteractionCommandHandler) {
        this.articleInteractionCommandHandler = articleInteractionCommandHandler;
        this.commentInteractionCommandHandler = commentInteractionCommandHandler;
    }

    @Transactional
    @Override
    public void handleLike(InteractionCommand interactionCommand) {
        switch (interactionCommand.getTargetType()) {
            case ARTICLE -> articleInteractionCommandHandler.handleLike(interactionCommand);
            case COMMENT -> commentInteractionCommandHandler.handleLike(interactionCommand);
            default -> throw new BlogDomainException("不支持的操作");
        }
    }

    @Override
    public void handleFavorite(InteractionCommand interactionCommand) {
        articleInteractionCommandHandler.handleFavorite(interactionCommand);
    }
}
