package com.jackmouse.system.blog.application;

import com.jackmouse.system.blog.application.dto.create.CreateArticleCommand;
import com.jackmouse.system.blog.application.dto.create.CreateArticleResponse;
import com.jackmouse.system.blog.application.dto.query.ArticleIdQuery;
import com.jackmouse.system.blog.application.dto.query.ArticleResponse;
import com.jackmouse.system.blog.application.dto.query.QueryMainSortCategoryArticlesResponse;
import com.jackmouse.system.blog.application.mapper.ArticleDataMapper;
import com.jackmouse.system.blog.application.ports.input.service.ArticleApplicationService;
import com.jackmouse.system.blog.domain.article.entity.Article;
import com.jackmouse.system.blog.domain.article.event.ArticleCreatedEvent;
import com.jackmouse.system.blog.domain.article.query.ArticleSummary;
import com.jackmouse.system.blog.domain.article.valueobject.ArticleId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * @ClassName ArticleQueryApplicationServiceImpl
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/7 14:28
 * @Version 1.0
 **/
@Slf4j
@Validated
@Service
public class ArticleApplicationServiceImpl implements ArticleApplicationService {

    private final ArticleQueryCommandHandler articleQueryCommandHandler;
    private final ArticleCreateCommandHandler articleCreateCommandHandler;

    public ArticleApplicationServiceImpl(ArticleQueryCommandHandler articleQueryCommandHandler, ArticleCreateCommandHandler articleCreateCommandHandler, ArticleDataMapper articleDataMapper, ApplicationDomainEventPublisher applicationDomainEventPublisher) {

        this.articleQueryCommandHandler = articleQueryCommandHandler;
        this.articleCreateCommandHandler = articleCreateCommandHandler;
    }

    @Override
    public List<QueryMainSortCategoryArticlesResponse> queryMainSortCategoryArticles() {
        return articleQueryCommandHandler.queryMainSortCategoryArticles();
    }

    @Override
    public ArticleResponse queryArticleById(ArticleIdQuery articleIdQuery) {
        return articleQueryCommandHandler.queryArticleById(articleIdQuery);
    }

    @Override
    public CreateArticleResponse createArticle(CreateArticleCommand createArticleCommand) {
        return articleCreateCommandHandler.createArticle(createArticleCommand);
    }
}
