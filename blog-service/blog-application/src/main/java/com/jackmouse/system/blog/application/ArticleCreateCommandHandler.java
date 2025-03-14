package com.jackmouse.system.blog.application;

import com.jackmouse.system.blog.application.dto.create.CreateArticleCommand;
import com.jackmouse.system.blog.application.dto.create.CreateArticleResponse;
import com.jackmouse.system.blog.application.mapper.ArticleDataMapper;
import com.jackmouse.system.blog.domain.article.ArticleDomainService;
import com.jackmouse.system.blog.domain.article.entity.Article;
import com.jackmouse.system.blog.domain.article.event.ArticleCreatedEvent;
import com.jackmouse.system.blog.domain.article.repository.ArticleRepository;
import com.jackmouse.system.blog.domain.exception.BlogDomainException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName ArticleCreateCommandHandler
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/14 11:10
 * @Version 1.0
 **/
@Slf4j
@Component
public class ArticleCreateCommandHandler {
    private final ArticleDataMapper articleDataMapper;
    private final ArticleDomainService articleDomainService;
    private final ArticleRepository articleRepository;
    private final ApplicationDomainEventPublisher applicationDomainEventPublisher;

    public ArticleCreateCommandHandler(ArticleDataMapper articleDataMapper, ArticleDomainService articleDomainService, ArticleRepository articleRepository, ApplicationDomainEventPublisher applicationDomainEventPublisher) {
        this.articleDataMapper = articleDataMapper;
        this.articleDomainService = articleDomainService;
        this.articleRepository = articleRepository;
        this.applicationDomainEventPublisher = applicationDomainEventPublisher;
    }

    @Transactional
    public CreateArticleResponse createArticle(CreateArticleCommand createArticleCommand) {
        Article article = articleDataMapper.createArticleCommandToArticle(createArticleCommand);
        ArticleCreatedEvent articleCreatedEvent = articleDomainService.validateAndInitiateArticle(article);
        saveArticle(article);
        log.info("Article created with ID: {}", article.getId().getValue());
        CreateArticleResponse createArticleResponse = articleDataMapper
                .articleToCreateArticleResponse(articleCreatedEvent.getArticle());
        log.info("Returning CreateArticleResponse with ID: {}", articleCreatedEvent.getArticle().getId());
        applicationDomainEventPublisher.publish(articleCreatedEvent);
        return createArticleResponse;
    }

    private void saveArticle(Article article) {
        Article savedArticle = articleRepository.save(article);
        if (savedArticle == null) {
            log.error("Failed to save article: {}", article.getId());
            throw new BlogDomainException("Failed to save article");
        }
    }
}
