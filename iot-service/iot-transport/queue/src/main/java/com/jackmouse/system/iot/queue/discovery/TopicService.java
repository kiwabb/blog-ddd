package com.jackmouse.system.iot.queue.discovery;

import com.jackmouse.system.iot.message.queue.TopicPartitionInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ConcurrentReferenceHashMap;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @ClassName TopicService
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/2 09:38
 * @Version 1.0
 **/
@Service
public class TopicService {
    @Value("${queue.prefix:}")
    private String prefix;

    @Value("${queue.core.notifications-topic:tb_core.notifications}")
    private String tbCoreNotificationsTopic;

    @Value("${queue.rule-engine.notifications-topic:tb_rule_engine.notifications}")
    private String tbRuleEngineNotificationsTopic;

    @Value("${queue.transport.notifications-topics:tb_transport.notifications}")
    private String tbTransportNotificationsTopic;

    @Value("${queue.edge.notifications-topic:tb_edge.notifications}")
    private String tbEdgeNotificationsTopic;

    @Value("${queue.edge.event-notifications-topic:tb_edge_event.notifications}")
    private String tbEdgeEventNotificationsTopic;

    @Value("${queue.calculated_fields.notifications-topic:calculated_field.notifications}")
    private String tbCalculatedFieldNotificationsTopic;

    private final ConcurrentMap<String, TopicPartitionInfo> tbCoreNotificationTopics = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, TopicPartitionInfo> tbRuleEngineNotificationTopics = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, TopicPartitionInfo> tbEdgeNotificationTopics = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, TopicPartitionInfo> tbCalculatedFieldNotificationTopics = new ConcurrentHashMap<>();
    private final ConcurrentReferenceHashMap<Object, TopicPartitionInfo> tbEdgeEventsNotificationTopics = new ConcurrentReferenceHashMap<>();

    public String buildTopicName(String topic) {
        return prefix.isBlank() ? topic : prefix + "." + topic;
    }
}
