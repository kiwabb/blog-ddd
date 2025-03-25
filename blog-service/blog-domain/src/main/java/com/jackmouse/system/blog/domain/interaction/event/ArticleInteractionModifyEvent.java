package com.jackmouse.system.blog.domain.interaction.event;

import com.jackmouse.system.blog.domain.interaction.entity.Like;

import java.time.ZonedDateTime;

/**
 * @ClassName ArticleInteractionMotifyEvent
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/21 10:56
 * @Version 1.0
 **/
public class ArticleInteractionModifyEvent extends ArticleInteractionEvent{
    public ArticleInteractionModifyEvent(Like like, ZonedDateTime zonedDateTime) {
        super(like, zonedDateTime);
    }
}
