package com.jackmouse.system.blog.domain.event.publish;

import com.jackmouse.system.blog.domain.event.DomainEvent;

/**
 * @ClassName DomainEventPublisher
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/14 13:33
 * @Version 1.0
 **/
public interface DomainEventPublisher<T extends DomainEvent<?>> {
    void publish(T domainEvent);
}
