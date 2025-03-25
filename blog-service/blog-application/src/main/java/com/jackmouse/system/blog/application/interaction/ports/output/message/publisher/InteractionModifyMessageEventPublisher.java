package com.jackmouse.system.blog.application.interaction.ports.output.message.publisher;

import com.jackmouse.system.blog.domain.event.publish.DomainEventPublisher;
import com.jackmouse.system.blog.domain.interaction.event.ArticleInteractionEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

/**
 * @ClassName InterationModifyMessagePublisher
 * @Description 文章/评论交互事件发布(Spring Event)
 * @Author zhoujiaangyao
 * @Date 2025/3/21 11:03
 * @Version 1.0
 **/
@Slf4j
@Component
public class InteractionModifyMessageEventPublisher implements ApplicationEventPublisherAware, DomainEventPublisher<ArticleInteractionEvent> {
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publish(ArticleInteractionEvent domainEvent) {
        applicationEventPublisher.publishEvent(domainEvent);
        log.info("ArticleInteractionEvent published with ID: {}", domainEvent.getArticleInteraction().getId().getValue());
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
