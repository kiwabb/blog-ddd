package com.jackmouse.system.blog.domain.article;


import com.jackmouse.system.blog.domain.article.entity.Article;
import com.jackmouse.system.blog.domain.article.event.ArticleCreatedEvent;

import java.time.ZonedDateTime;

public class ArticleDomainServiceImpl implements ArticleDomainService {

    @Override
    public ArticleCreatedEvent validateAndInitiateArticle(Article article) {
        article.validateArticle();
        article.initializeArticle();
        return new ArticleCreatedEvent(article, ZonedDateTime.now());
    }

}