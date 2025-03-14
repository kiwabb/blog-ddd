package com.jackmouse.system.blog.domain.article.event;

import com.jackmouse.system.blog.domain.article.entity.Article;

import java.time.ZonedDateTime;

/**
 * @ClassName ArticleCreatedEvent
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/14 11:26
 * @Version 1.0
 **/
public class ArticleCreatedEvent extends ArticleEvent{
    public ArticleCreatedEvent(Article article, ZonedDateTime zonedDateTime) {
        super(article, zonedDateTime);
    }
}
