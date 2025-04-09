package com.jackmouse.system.iot.transport.mqtt.session;

import java.util.regex.Pattern;

/**
 * @ClassName MqttTopicMatcher
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/7 15:40
 * @Version 1.0
 **/
public class MqttTopicMatcher {
    private final String topic;
    private final Pattern topicRegex;
    public MqttTopicMatcher(String topic) {
        if (topic == null) {
            throw new NullPointerException("topic");
        }
        this.topic = topic;
        this.topicRegex = Pattern.compile(topic.replace("+", "[^/]+").replace("#", ".+") + "$");

    }
}
