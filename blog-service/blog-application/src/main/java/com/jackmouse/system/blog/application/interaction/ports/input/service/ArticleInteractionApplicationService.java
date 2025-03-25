package com.jackmouse.system.blog.application.interaction.ports.input.service;

import com.jackmouse.system.blog.application.interaction.dto.InteractionCommand;

/**
 * @ClassName ArticleApplicationService
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/7 14:22
 * @Version 1.0
 **/
public interface ArticleInteractionApplicationService {

    void handleLike(InteractionCommand interactionCommand);

    void handleFavorite(InteractionCommand interactionCommand);
}
