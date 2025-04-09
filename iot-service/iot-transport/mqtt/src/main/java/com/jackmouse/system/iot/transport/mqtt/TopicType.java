package com.jackmouse.system.iot.transport.mqtt;

import com.jackmouse.system.iot.common.constant.MqttTopics;
import lombok.Getter;

/**
 * @ClassName TopicType
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/8 13:32
 * @Version 1.0
 **/
public enum TopicType {
    V1(MqttTopics.DEVICE_ATTRIBUTES_RESPONSE_TOPIC_PREFIX, MqttTopics.DEVICE_ATTRIBUTES_TOPIC, MqttTopics.DEVICE_RPC_REQUESTS_TOPIC, MqttTopics.DEVICE_RPC_RESPONSE_TOPIC);

    @Getter
    private final String attributesResponseTopicBase;

    @Getter
    private final String attributesSubTopic;

    @Getter
    private final String rpcRequestTopicBase;

    @Getter
    private final String rpcResponseTopicBase;

    TopicType(String attributesRequestTopicBase, String attributesSubTopic, String rpcRequestTopicBase, String rpcResponseTopicBase) {
        this.attributesResponseTopicBase = attributesRequestTopicBase;
        this.attributesSubTopic = attributesSubTopic;
        this.rpcRequestTopicBase = rpcRequestTopicBase;
        this.rpcResponseTopicBase = rpcResponseTopicBase;
    }
}
