package com.jackmouse.system.blog.domain.article.event;

import com.jackmouse.system.blog.domain.article.entity.Article;
import com.jackmouse.system.blog.domain.event.DomainEvent;

import java.time.ZonedDateTime;

/**
 * @ClassName ArticleEvent
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/14 11:27
 * @Version 1.0
 **/
public abstract class ArticleEvent implements DomainEvent<Article> {
    private final Article article;
    private final ZonedDateTime zonedDateTime;

    protected ArticleEvent(Article article, ZonedDateTime zonedDateTime) {
        this.article = article;
        this.zonedDateTime = zonedDateTime;
    }

    public Article getArticle() {
        return article;
    }

    public ZonedDateTime getZonedDateTime() {
        return zonedDateTime;
    }
}
