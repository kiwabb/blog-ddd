package com.jackmouse.system.blog.domain.interaction.event;

import com.jackmouse.system.blog.domain.event.DomainEvent;
import com.jackmouse.system.blog.domain.interaction.entity.Like;

import java.time.ZonedDateTime;

/**
 * @ClassName InteractionEvent
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/21 10:53
 * @Version 1.0
 **/
public abstract class ArticleInteractionEvent implements DomainEvent<Like> {
    private final Like like;
    private final ZonedDateTime zonedDateTime;

    public ArticleInteractionEvent(Like like, ZonedDateTime zonedDateTime) {
        this.like = like;
        this.zonedDateTime = zonedDateTime;
    }
    public Like getArticleInteraction() {
        return like;
    }
    public ZonedDateTime getZonedDateTime() {
        return zonedDateTime;
    }
}
