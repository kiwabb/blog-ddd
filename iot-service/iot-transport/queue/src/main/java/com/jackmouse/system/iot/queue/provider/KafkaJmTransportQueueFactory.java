package com.jackmouse.system.iot.queue.provider;

import com.jackmouse.server.gen.transport.TransportProtos;
import com.jackmouse.system.iot.queue.JmQueueAdmin;
import com.jackmouse.system.iot.queue.JmQueueMsg;
import com.jackmouse.system.iot.queue.JmQueueRequestTemplate;
import com.jackmouse.system.iot.queue.common.DefaultJmQueueRequestTemplate;
import com.jackmouse.system.iot.queue.common.JmProtoQueueMsg;
import com.jackmouse.system.iot.queue.discovery.JmServiceInfoProvider;
import com.jackmouse.system.iot.queue.discovery.TopicService;
import com.jackmouse.system.iot.queue.kafka.*;
import com.jackmouse.system.iot.queue.settings.JmQueueTransportApiSettings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

/**
 * @ClassName KafkaJmTransportQueueFactory
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/3 16:29
 * @Version 1.0
 **/
@Component
@ConditionalOnExpression("'${queue.type:null}'=='kafka' && (('${service.type:null}'=='monolith' && '${transport.api_enabled:true}'=='true') || '${service.type:null}'=='tb-transport')")
@Slf4j
public class KafkaJmTransportQueueFactory implements JmTransportQueueFactory{

    private final JmKafkaSettings kafkaSettings;
    private final JmServiceInfoProvider serviceInfoProvider;
    private final JmQueueTransportApiSettings transportApiSettings;
    private final TopicService topicService;
    private final JmKafkaConsumerStatsService consumerStatsService;
    private final JmQueueAdmin transportApiRequestAdmin;
    private final JmQueueAdmin transportApiResponseAdmin;

    public KafkaJmTransportQueueFactory(JmKafkaSettings kafkaSettings,
                                        JmServiceInfoProvider serviceInfoProvider,
                                        JmQueueTransportApiSettings transportApiSettings,
                                        JmKafkaTopicConfigs kafkaTopicConfigs,
                                        TopicService topicService,
                                        JmKafkaConsumerStatsService consumerStatsService) {
        this.kafkaSettings = kafkaSettings;
        this.serviceInfoProvider = serviceInfoProvider;
        this.transportApiSettings = transportApiSettings;
        this.topicService = topicService;
        this.consumerStatsService = consumerStatsService;
        this.transportApiRequestAdmin = new JmKafkaAdmin(kafkaSettings, kafkaTopicConfigs.getTransportApiRequestConfigs());
        this.transportApiResponseAdmin = new JmKafkaAdmin(kafkaSettings, kafkaTopicConfigs.getTransportApiResponseConfigs());
    }

    @Override
    public JmQueueRequestTemplate<
            JmProtoQueueMsg<TransportProtos.TransportApiRequestMsg>,
            JmProtoQueueMsg<TransportProtos.TransportApiResponseMsg>> createTransportApiRequestTemplate() {
        JmKafkaProducerTemplate.JmKafkaProducerTemplateBuilder<JmProtoQueueMsg<TransportProtos.TransportApiRequestMsg>>
                requestBuilder = JmKafkaProducerTemplate.builder();
        requestBuilder.settings(kafkaSettings);
        requestBuilder.clientId("transport-api-request-" + serviceInfoProvider.getServiceId());
        requestBuilder.defaultTopic(topicService.buildTopicName(transportApiSettings.getRequestsTopic()));
        requestBuilder.admin(transportApiRequestAdmin);

        JmKafkaConsumerTemplate.JmKafkaConsumerTemplateBuilder<JmProtoQueueMsg<TransportProtos.TransportApiResponseMsg>>
                responseBuilder = JmKafkaConsumerTemplate.builder();
        responseBuilder.settings(kafkaSettings);
        responseBuilder.topic(topicService.buildTopicName(transportApiSettings.getResponsesTopic() + "." + serviceInfoProvider.getServiceId()));
        responseBuilder.clientId("transport-api-response-" + serviceInfoProvider.getServiceId());
        responseBuilder.groupId(topicService.buildTopicName("transport-node-" + serviceInfoProvider.getServiceId()));
        responseBuilder.decoder(msg -> new JmProtoQueueMsg<>(msg.getKey(), TransportProtos.TransportApiResponseMsg.parseFrom(msg.getData()), msg.getHeaders()));
        responseBuilder.admin(transportApiResponseAdmin);
        responseBuilder.statsService(consumerStatsService);
        DefaultJmQueueRequestTemplate.DefaultJmQueueRequestTemplateBuilder
                <JmProtoQueueMsg<TransportProtos.TransportApiRequestMsg>, JmProtoQueueMsg<TransportProtos.TransportApiResponseMsg>>
                templateBuilder = DefaultJmQueueRequestTemplate.builder();
        templateBuilder.queueAdmin(transportApiResponseAdmin);
        templateBuilder.requestTemplate(requestBuilder.build());
        templateBuilder.responseTemplate(responseBuilder.build());
        templateBuilder.maxPendingRequests(transportApiSettings.getMaxPendingRequests());
        templateBuilder.maxRequestTimeout(transportApiSettings.getMaxRequestsTimeout());
        templateBuilder.pollInterval(transportApiSettings.getResponsePollInterval());
        return templateBuilder.build();
    }
}
