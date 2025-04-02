package com.jackmouse.system.iot.queue.provider;

import com.jackmouse.server.gen.transport.TransportProtos;
import com.jackmouse.system.iot.queue.JmQueueMsg;
import com.jackmouse.system.iot.queue.JmQueueRequestTemplate;
import com.jackmouse.system.iot.queue.TbQueueAdmin;
import com.jackmouse.system.iot.queue.common.DefaultJmQueueRequestTemplate;
import com.jackmouse.system.iot.queue.common.JmProtoQueueMsg;
import com.jackmouse.system.iot.queue.discovery.TopicService;
import com.jackmouse.system.iot.queue.memory.InMemoryJmQueueConsumer;
import com.jackmouse.system.iot.queue.memory.InMemoryJmQueueProducer;
import com.jackmouse.system.iot.queue.memory.InMemoryStorage;
import com.jackmouse.system.iot.queue.settings.JmQueueTransportApiSettings;
import org.springframework.stereotype.Component;

/**
 * @ClassName InMemoryJmTransportQueueFactory
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/2 08:47
 * @Version 1.0
 **/
@Component
public class InMemoryJmTransportQueueFactory implements JmTransportQueueFactory{

    private final JmQueueTransportApiSettings transportApiSettings;
    private final InMemoryStorage<JmQueueMsg> storage;
    private final TopicService topicService;

    public InMemoryJmTransportQueueFactory(JmQueueTransportApiSettings transportApiSettings, InMemoryStorage<JmQueueMsg> storage, TopicService topicService) {
        this.transportApiSettings = transportApiSettings;
        this.storage = storage;
        this.topicService = topicService;
    }

    @Override
    public JmQueueRequestTemplate<
            JmProtoQueueMsg<TransportProtos.TransportApiRequestMsg>,
            JmProtoQueueMsg<TransportProtos.TransportApiResponseMsg>> createTransportApiRequestTemplate() {
        InMemoryJmQueueProducer<JmProtoQueueMsg<TransportProtos.TransportApiRequestMsg>> producerTemplate =
                new InMemoryJmQueueProducer<>(storage, topicService.buildTopicName(transportApiSettings.getRequestsTopic()));

        InMemoryJmQueueConsumer<JmProtoQueueMsg<TransportProtos.TransportApiResponseMsg>> consumerTemplate =
                new InMemoryJmQueueConsumer<>(storage, topicService.buildTopicName(transportApiSettings.getResponsesTopic()));

        DefaultJmQueueRequestTemplate.DefaultJmQueueRequestTemplateBuilder<
                JmProtoQueueMsg<TransportProtos.TransportApiRequestMsg>,
                JmProtoQueueMsg<TransportProtos.TransportApiResponseMsg>> templateBuilder =
                DefaultJmQueueRequestTemplate.builder();
        templateBuilder.queueAdmin(new TbQueueAdmin() {
            @Override
            public void createTopicIfNotExists(String topic, String properties) {
            }

            @Override
            public void destroy() {
            }

            @Override
            public void deleteTopic(String topic) {
            }
        });
        templateBuilder.requestTemplate(producerTemplate);
        templateBuilder.responseTemplate(consumerTemplate);
        templateBuilder.maxPendingRequests(transportApiSettings.getMaxPendingRequests());
        templateBuilder.maxRequestTimeout(transportApiSettings.getMaxRequestsTimeout());
        templateBuilder.pollInterval(transportApiSettings.getResponsePollInterval());
        return templateBuilder.build();
    }
}
