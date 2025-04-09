package com.jackmouse.system.iot.transport.mqtt.util;

import com.jackmouse.system.iot.common.constant.MqttTopics;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @ClassName MqttTopicFilterFactory
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/8 11:13
 * @Version 1.0
 **/
@Slf4j
public class MqttTopicFilterFactory {
    private static final ConcurrentMap<String, MqttTopicFilter> filters = new ConcurrentHashMap<>();

    private static final MqttTopicFilter DEFAULT_TELEMETRY_TOPIC_FILTER = toFilter(MqttTopics.DEVICE_TELEMETRY_TOPIC);
    public static MqttTopicFilter toFilter(String topicFilter) {
        if (topicFilter == null || topicFilter.isEmpty()) {
            throw new IllegalArgumentException("Topic filter can't be empty!");
        }
        return filters.computeIfAbsent(topicFilter, filter -> {
            if (filter.equals("#")) {
                return new AlwaysTrueTopicFilter();
            } else if (filter.contains("+") || filter.contains("#")) {
                String regex = filter
                        .replace("\\", "\\\\")
                        .replace("+", "[^/]+")
                        .replace("/#", "($|/.*)");
                log.debug("Converting [{}] to [{}]", filter, regex);
                return new RegexTopicFilter(regex);
            } else {
                return new EqualsTopicFilter(filter);
            }
        });
    }

    public static MqttTopicFilter getDefaultTelemetryFilter() {
        return DEFAULT_TELEMETRY_TOPIC_FILTER;
    }
}
