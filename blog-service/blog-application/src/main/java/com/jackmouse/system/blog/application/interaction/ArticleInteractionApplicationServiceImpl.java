package com.jackmouse.system.blog.application.interaction;

import com.jackmouse.system.blog.application.interaction.dto.InteractionCommand;
import com.jackmouse.system.blog.application.interaction.ports.input.service.ArticleInteractionApplicationService;
import org.springframework.stereotype.Service;

/**
 * @ClassName ArticleInteractionApplicationServiceImpl
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/21 09:58
 * @Version 1.0
 **/
@Service
public class ArticleInteractionApplicationServiceImpl implements ArticleInteractionApplicationService {
    private final ArticleInteractionCommandHandler articleInteractionCommandHandler;

    public ArticleInteractionApplicationServiceImpl(ArticleInteractionCommandHandler articleInteractionCommandHandler) {
        this.articleInteractionCommandHandler = articleInteractionCommandHandler;
    }

    @Override
    public void handleLike(InteractionCommand interactionCommand) {
        articleInteractionCommandHandler.handleLike(interactionCommand);
    }

    @Override
    public void handleFavorite(InteractionCommand interactionCommand) {
        articleInteractionCommandHandler.handleFavorite(interactionCommand);
    }
}
