package com.jackmouse.system.iot.queue.provider;

import com.jackmouse.server.gen.transport.TransportProtos;
import com.jackmouse.system.iot.queue.JmQueueAdmin;
import com.jackmouse.system.iot.queue.JmQueueConsumer;
import com.jackmouse.system.iot.queue.JmQueueMsg;
import com.jackmouse.system.iot.queue.JmQueueProducer;
import com.jackmouse.system.iot.queue.common.JmProtoQueueMsg;
import com.jackmouse.system.iot.queue.discovery.JmServiceInfoProvider;
import com.jackmouse.system.iot.queue.discovery.TopicService;
import com.jackmouse.system.iot.queue.kafka.*;
import com.jackmouse.system.iot.queue.settings.JmQueueTransportApiSettings;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @ClassName KafkaMonolithQueueFactory
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/3 15:34
 * @Version 1.0
 **/
@Component
@ConditionalOnExpression("'${queue.type}'=='kafka' && '${service.type:null}' =='monolith'")
public class KafkaMonolithQueueFactory implements JmCoreQueueFactory{

    private final TopicService topicService;
    private final JmKafkaSettings kafkaSettings;
    private final JmServiceInfoProvider serviceInfoProvider;
    private final JmQueueTransportApiSettings transportApiSettings;

    private final JmKafkaConsumerStatsService consumerStatsService;
    private final JmQueueAdmin coreAdmin;
    private final JmQueueAdmin transportApiResponseAdmin;
    private final JmQueueAdmin transportApiRequestAdmin;

    private final AtomicLong consumerCount = new AtomicLong();

    public KafkaMonolithQueueFactory(TopicService topicService,
                                     JmKafkaSettings kafkaSettings,
                                     JmServiceInfoProvider serviceInfoProvider,
                                     JmQueueTransportApiSettings transportApiSettings,
                                     JmKafkaTopicConfigs kafkaTopicConfigs,
                                     JmKafkaConsumerStatsService consumerStatsService) {
        this.topicService = topicService;
        this.kafkaSettings = kafkaSettings;
        this.serviceInfoProvider = serviceInfoProvider;
        this.transportApiSettings = transportApiSettings;
        this.consumerStatsService = consumerStatsService;
        this.coreAdmin = new JmKafkaAdmin(kafkaSettings, kafkaTopicConfigs.getCoreConfigs());
        this.transportApiResponseAdmin = new JmKafkaAdmin(kafkaSettings, kafkaTopicConfigs.getTransportApiResponseConfigs());
        this.transportApiRequestAdmin = new JmKafkaAdmin(kafkaSettings, kafkaTopicConfigs.getTransportApiRequestConfigs());
    }


    @Override
    public JmQueueProducer<JmProtoQueueMsg<TransportProtos.TransportApiResponseMsg>> createTransportApiResponseProducer() {
        JmKafkaProducerTemplate.JmKafkaProducerTemplateBuilder<JmProtoQueueMsg<TransportProtos.TransportApiResponseMsg>> builder = JmKafkaProducerTemplate.builder();
        builder.settings(kafkaSettings);
        builder.clientId("monolith-transport-api-producer-" + serviceInfoProvider.getServiceId());
        builder.defaultTopic(topicService.buildTopicName(transportApiSettings.getResponsesTopic()));
        builder.admin(transportApiResponseAdmin);
        return builder.build();
    }

    @Override
    public JmQueueConsumer<JmProtoQueueMsg<TransportProtos.TransportApiRequestMsg>> createTransportApiRequestConsumer() {
        JmKafkaConsumerTemplate.JmKafkaConsumerTemplateBuilder<JmProtoQueueMsg<TransportProtos.TransportApiRequestMsg>> builder = JmKafkaConsumerTemplate.builder();
        builder.settings(kafkaSettings);
        builder.clientId("monolith-transport-api-consumer-" + serviceInfoProvider.getServiceId());
        builder.topic(topicService.buildTopicName(transportApiSettings.getRequestsTopic()));
        builder.groupId(topicService.buildTopicName("monolith-transport-api-consumer"));
        builder.decoder(msg -> new JmProtoQueueMsg<>(msg.getKey(), TransportProtos.TransportApiRequestMsg.parseFrom(msg.getData()), msg.getHeaders()));
        builder.admin(transportApiRequestAdmin);
        builder.statsService(consumerStatsService);
        return builder.build();
    }
}
