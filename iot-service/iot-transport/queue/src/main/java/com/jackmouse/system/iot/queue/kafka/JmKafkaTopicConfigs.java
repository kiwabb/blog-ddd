package com.jackmouse.system.iot.queue.kafka;

import com.jackmouse.system.iot.queue.util.PropertyUtils;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @ClassName JmKafkaTopicConfigs
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/3 15:40
 * @Version 1.0
 **/
@Getter
@Component
@ConditionalOnProperty(prefix = "queue", value = "type", havingValue = "kafka")
public class JmKafkaTopicConfigs {

    @Value("${queue.kafka.topic-properties.core:}")
    private String coreProperties;

    @Value("${queue.kafka.topic-properties.transport-api:}")
    private String transportApiProperties;

    public static final String NUM_PARTITIONS_SETTING = "partitions";
    private Map<String, String> coreConfigs;
    private Map<String, String> transportApiResponseConfigs;
    private Map<String, String> transportApiRequestConfigs;
    @PostConstruct
    private void init() {
        coreConfigs = PropertyUtils.getProps(coreProperties);
        transportApiRequestConfigs = PropertyUtils.getProps(transportApiProperties);
        transportApiResponseConfigs = PropertyUtils.getProps(transportApiProperties);
    }


}
