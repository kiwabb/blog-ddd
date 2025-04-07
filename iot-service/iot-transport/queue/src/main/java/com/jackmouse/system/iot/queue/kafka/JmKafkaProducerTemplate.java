package com.jackmouse.system.iot.queue.kafka;

import com.jackmouse.system.iot.message.queue.TopicPartitionInfo;
import com.jackmouse.system.iot.queue.JmQueueAdmin;
import com.jackmouse.system.iot.queue.JmQueueCallback;
import com.jackmouse.system.iot.queue.JmQueueMsg;
import com.jackmouse.system.iot.queue.JmQueueProducer;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @ClassName JmKafkaProducerTemplate
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/3 13:15
 * @Version 1.0
 **/
@Slf4j
public class JmKafkaProducerTemplate<T extends JmQueueMsg> implements JmQueueProducer<T> {
    private final KafkaProducer<String, byte[]> producer;
    private final JmKafkaSettings settings;
    @Getter
    private final String defaultTopic;
    @Getter
    private final String clientId;
    private final JmQueueAdmin admin;
    private final Set<String> topics;

    @Builder
    private JmKafkaProducerTemplate(JmKafkaSettings settings, String defaultTopic, String clientId, JmQueueAdmin admin) {
        Properties props = settings.toProducerProperties();

        this.clientId = Objects.requireNonNull(clientId, "Kafka producer client.id is null");
        if (!StringUtils.isEmpty(clientId)) {
            props.put(ProducerConfig.CLIENT_ID_CONFIG, clientId);
        }
        this.settings = settings;
        this.producer = new KafkaProducer<>(props);
        this.defaultTopic = defaultTopic;
        this.admin = admin;
        topics = ConcurrentHashMap.newKeySet();
    }


    @Override
    public void init() {

    }

    void addAnalyticHeaders(List<Header> headers) {
        headers.add(new RecordHeader("_producerId", getClientId().getBytes(StandardCharsets.UTF_8)));
        headers.add(new RecordHeader("_threadName", Thread.currentThread().getName().getBytes(StandardCharsets.UTF_8)));
        if (log.isTraceEnabled()) {
            try {
                StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
                int maxLevel = Math.min(stackTrace.length, 20);
                for (int i = 2; i < maxLevel; i++) { // ignore two levels: getStackTrace and addAnalyticHeaders
                    headers.add(new RecordHeader("_stackTrace" + i, stackTrace[i].toString().getBytes(StandardCharsets.UTF_8)));
                }
            } catch (Throwable t) {
                log.trace("Failed to add stacktrace headers in Kafka producer {}", getClientId(), t);
            }
        }
    }

    @Override
    public void send(TopicPartitionInfo topicPartitionInfo, T msg, JmQueueCallback callback) {
        send(topicPartitionInfo, msg.getKey().toString(), msg, callback);
    }

    public void send(TopicPartitionInfo tpi, String key, T msg, JmQueueCallback callback) {
        try {
            String topic = tpi.getFullTopicName();
            createTopicIfNotExist(topic);
            byte[] data = msg.getData();
            ProducerRecord<String, byte[]> record;
            List<Header> headers = msg.getHeaders().getData().entrySet().stream()
                    .map(e -> new RecordHeader(e.getKey(), e.getValue())).collect(Collectors.toList());
            if (log.isDebugEnabled()) {
                addAnalyticHeaders(headers);
            }
            Integer partition = tpi.isUseInternalPartition() ? tpi.getPartition().orElse(null) : null;
            record = new ProducerRecord<>(topic, partition, key, data, headers);
            producer.send(record, (metadata, exception) -> {
                if (exception == null) {
                    if (callback != null) {
                        callback.onSuccess(new KafkaJmQueueMsgMetadata(metadata));
                    }
                } else {
                    if (callback != null) {
                        callback.onFailure(exception);
                    } else {
                        log.error("Failed to send message to Kafka topic {}", topic, exception);
                    }
                }
            });
        } catch (Exception e) {
            if (callback != null) {
                callback.onFailure(e);
            } else {
                log.warn("Producer template failure (send method wrapper): {}", e.getMessage(), e);
            }
            throw e;
        }
    }

    private void createTopicIfNotExist(String topic) {
        if (topics.contains(topic)) {
            return;
        }
        admin.createTopicIfNotExists(topic);
        topics.add(topic);
    }

    @Override
    public void stop() {
        if (producer != null) {
            producer.close();
        }
    }
}
