package com.jackmouse.system.iot.queue.provider;

import com.jackmouse.server.gen.transport.TransportProtos;
import com.jackmouse.system.iot.queue.JmQueueConsumer;
import com.jackmouse.system.iot.queue.JmQueueProducer;
import com.jackmouse.system.iot.queue.common.JmProtoQueueMsg;
import com.jackmouse.system.iot.queue.discovery.TopicService;
import com.jackmouse.system.iot.queue.memory.InMemoryJmQueueConsumer;
import com.jackmouse.system.iot.queue.memory.InMemoryJmQueueProducer;
import com.jackmouse.system.iot.queue.memory.InMemoryStorage;
import com.jackmouse.system.iot.queue.settings.JmQueueTransportApiSettings;
import org.springframework.stereotype.Component;

/**
 * @ClassName InMemoryMonolithQueueFactory
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/2 17:06
 * @Version 1.0
 **/
@Component
public class InMemoryMonolithQueueFactory implements JmCoreQueueFactory{

    private final InMemoryStorage storage;
    private final TopicService topicService;
    private final JmQueueTransportApiSettings transportApiSettings;

    public InMemoryMonolithQueueFactory(InMemoryStorage storage, TopicService topicService, JmQueueTransportApiSettings transportApiSettings) {
        this.storage = storage;
        this.topicService = topicService;
        this.transportApiSettings = transportApiSettings;
    }

    @Override
    public JmQueueProducer<JmProtoQueueMsg<TransportProtos.TransportApiResponseMsg>> createTransportApiResponseProducer() {
        return new InMemoryJmQueueProducer<>(storage, topicService.buildTopicName(transportApiSettings.getResponsesTopic()));
    }

    @Override
    public JmQueueConsumer<JmProtoQueueMsg<TransportProtos.TransportApiRequestMsg>> createTransportApiRequestConsumer() {
        return new InMemoryJmQueueConsumer<>(storage, topicService.buildTopicName(transportApiSettings.getRequestsTopic()));
    }
}
