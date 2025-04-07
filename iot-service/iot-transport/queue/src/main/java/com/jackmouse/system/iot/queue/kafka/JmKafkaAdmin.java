package com.jackmouse.system.iot.queue.kafka;

import com.jackmouse.system.iot.queue.JmQueueAdmin;
import com.jackmouse.system.iot.queue.util.PropertyUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.errors.TopicExistsException;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

/**
 * @ClassName TbKafkaAdmin
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/3 15:39
 * @Version 1.0
 **/
@Slf4j
public class JmKafkaAdmin implements JmQueueAdmin {

    private final JmKafkaSettings settings;
    private final Map<String, String> topicConfigs;
    private final int numPartitions;
    private volatile Set<String> topics;
    private final short replicationFactor;

    public JmKafkaAdmin(JmKafkaSettings settings, Map<String, String> topicConfigs) {
        this.settings = settings;
        this.topicConfigs = topicConfigs;

        String numPartitionsStr = topicConfigs.get(JmKafkaTopicConfigs.NUM_PARTITIONS_SETTING);
        if (numPartitionsStr != null) {
            numPartitions = Integer.parseInt(numPartitionsStr);
        } else {
            numPartitions = 1;
        }
        replicationFactor = settings.getReplicationFactor();
    }

    @Override
    public void createTopicIfNotExists(String topic, String properties) {
        Set<String> topics = getTopics();
        if (topics.contains(topic)) {
            return;
        }
        try {
            Map<String, String> configs = PropertyUtils.getProps(topicConfigs, properties);
            configs.remove(JmKafkaTopicConfigs.NUM_PARTITIONS_SETTING);
            NewTopic newTopic = new NewTopic(topic, numPartitions, replicationFactor).configs(configs);
            createTopic(newTopic).values().get(topic).get();
            topics.add(topic);
        } catch (ExecutionException ee) {
            if (ee.getCause() instanceof TopicExistsException) {
                //do nothing
            } else {
                log.warn("[{}] Failed to create topic", topic, ee);
                throw new RuntimeException(ee);
            }
        } catch (Exception e) {
            log.warn("[{}] Failed to create topic", topic, e);
            throw new RuntimeException(e);
        }
    }
    public CreateTopicsResult createTopic(NewTopic topic) {
        return settings.getAdminClient().createTopics(Collections.singletonList(topic));
    }

    @Override
    public void destroy() {

    }

    @Override
    public void deleteTopic(String topic) {
        Set<String> topics = getTopics();
        if (topics.remove(topic)) {
            settings.getAdminClient().deleteTopics(Collections.singletonList(topic));
        } else {
            try {
                if (settings.getAdminClient().listTopics().names().get().contains(topic)) {
                    settings.getAdminClient().deleteTopics(Collections.singletonList(topic));
                } else {
                    log.warn("Kafka topic [{}] does not exist.", topic);
                }
            } catch (InterruptedException | ExecutionException e) {
                log.error("Failed to delete kafka topic [{}].", topic, e);
            }
        }
    }

    private Set<String> getTopics() {
        if (topics == null) {
            synchronized (this) {
                if (topics == null) {
                    topics = ConcurrentHashMap.newKeySet();
                    try {
                        topics.addAll(settings.getAdminClient().listTopics().names().get());
                    } catch (InterruptedException | ExecutionException e) {
                        log.error("Failed to get all topics.", e);
                    }
                }
            }
        }
        return topics;
    }
}
