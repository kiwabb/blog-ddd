package com.jackmouse.system.blog.application.article;

import com.jackmouse.system.blog.domain.article.event.ArticleCreatedEvent;
import com.jackmouse.system.blog.domain.event.publish.DomainEventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

/**
 * @ClassName ApplicationDomainEventPublisher
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/14 13:31
 * @Version 1.0
 **/
@Slf4j
@Component
public class ApplicationDomainEventPublisher implements ApplicationEventPublisherAware,
        DomainEventPublisher<ArticleCreatedEvent> {

    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }


    @Override
    public void publish(ArticleCreatedEvent domainEvent) {
        applicationEventPublisher.publishEvent(domainEvent);
        log.info("ArticleCreatedEvent published with ID: {}", domainEvent.getArticle().getId().getValue());
    }
}
