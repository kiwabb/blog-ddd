package com.jackmouse.system.iot.queue.kafka;

import com.jackmouse.system.iot.message.queue.TopicPartitionInfo;
import com.jackmouse.system.iot.queue.JmQueueAdmin;
import com.jackmouse.system.iot.queue.JmQueueMsg;
import com.jackmouse.system.iot.queue.common.AbstractJmQueueConsumerTemplate;
import lombok.Builder;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.springframework.util.StopWatch;

import java.io.IOException;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName JmKafkaConsumerTemplate
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/3 14:23
 * @Version 1.0
 **/
@Slf4j
public class JmKafkaConsumerTemplate<T extends JmQueueMsg>
        extends AbstractJmQueueConsumerTemplate<ConsumerRecord<String, byte[]>, T> {

    private final JmQueueAdmin admin;
    private final KafkaConsumer<String, byte[]> consumer;
    private final JmKafkaDecoder<T> decoder;
    private final JmKafkaConsumerStatsService statsService;
    private final String groupId;
    private final boolean readFromBeginning;
    private final boolean stopWhenRead;
    private int readCount;
    private Map<Integer, Long> endOffsets;

    @Builder
    private JmKafkaConsumerTemplate(JmKafkaSettings settings,
                                    JmQueueAdmin admin,
                                    JmKafkaDecoder<T> decoder,
                                    JmKafkaConsumerStatsService statsService,
                                    String groupId,
                                    boolean readFromBeginning,
                                    boolean stopWhenRead,
                                    String clientId,
            String topic) {
        super(topic);
        Properties props = settings.toConsumerProperties(topic);
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, clientId);
        if (groupId != null) {
            props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        }
        this.statsService = statsService;
        this.groupId = groupId;
        if (statsService != null) {
            statsService.registerClientGroup(groupId);
        }
        this.admin = admin;
        this.consumer = new KafkaConsumer<>(props);
        this.decoder = decoder;
        this.readFromBeginning = readFromBeginning;
        this.stopWhenRead = stopWhenRead;
    }

    @Override
    protected T decode(ConsumerRecord<String, byte[]> record) throws IOException {
        return decoder.decode(new KafkaJmQueueMsg(record));
    }

    @Override
    protected List<ConsumerRecord<String, byte[]>> doPoll(long durationInMillis) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        log.trace("poll topic {} maxDuration {}", getTopic(), durationInMillis);
        ConsumerRecords<String, byte[]> records = consumer.poll(Duration.ofMillis(durationInMillis));
        stopWatch.stop();
        log.trace("poll topic {} took {}ms", getTopic(), stopWatch.getTotalTimeMillis());
        List<ConsumerRecord<String, byte[]>> recordList;
        if (records.isEmpty()) {
            recordList = Collections.emptyList();
        } else {
            recordList = new ArrayList<>(256);
            records.forEach(record -> {
                recordList.add(record);
                if (stopWhenRead && endOffsets != null) {
                    readCount++;
                    int partition = record.partition();
                    Long endOffset = endOffsets.get(partition);
                    if (endOffset == null) {
                        log.debug("End offset not found for {} [{}]", record.topic(), partition);
                        return;
                    }
                    log.trace("[{}-{}] Got record offset {}, expected end offset: {}", record.topic(), partition, record.offset(), endOffset - 1);
                    if (record.offset() >= endOffset - 1) {
                        endOffsets.remove(partition);
                    }
                }
            });
        }
        if (stopWhenRead && endOffsets != null && endOffsets.isEmpty()) {
            log.info("Finished reading {}, processed {} messages", partitions, readCount);
            stop();
        }
        return recordList;
    }

    @Override
    protected void doSubscribe(Set<TopicPartitionInfo> partitions) {
        Map<String, List<Integer>> topics;
        if (partitions == null) {
            topics = Collections.emptyMap();
        } else {
            topics = new HashMap<>();
            partitions.forEach(tpi -> {
                if (tpi.isUseInternalPartition()) {
                    topics.computeIfAbsent(tpi.getFullTopicName(), t -> new ArrayList<>()).add(tpi.getPartition().get());
                } else {
                    topics.put(tpi.getFullTopicName(), null);
                }
            });
        }
        if (!topics.isEmpty()) {
            topics.keySet().forEach(admin::createTopicIfNotExists);
            List<String> toSubscribe = new ArrayList<>();
            topics.forEach((topic, kafkaPartitions) -> {
                if (kafkaPartitions == null) {
                    toSubscribe.add(topic);
                } else {
                    List<TopicPartition> topicPartitions = kafkaPartitions.stream()
                            .map(partition -> new TopicPartition(topic, partition))
                            .toList();
                    consumer.assign(topicPartitions);
                    onPartitionsAssigned(topicPartitions);
                }
            });
            if (!toSubscribe.isEmpty()) {
                if (readFromBeginning || stopWhenRead) {
                    consumer.subscribe(toSubscribe, new ConsumerRebalanceListener() {
                        @Override
                        public void onPartitionsRevoked(Collection<TopicPartition> partitions) {}

                        @Override
                        public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
                            log.debug("Handling onPartitionsAssigned {}", partitions);
                            JmKafkaConsumerTemplate.this.onPartitionsAssigned(partitions);
                        }
                    });
                } else {
                    consumer.subscribe(toSubscribe);
                }
            }
        } else {
            log.info("unsubscribe due to empty topic list");
            consumer.unsubscribe();
        }
    }



    private void onPartitionsAssigned(Collection<TopicPartition> partitions) {
        if (readFromBeginning) {
            consumer.seekToBeginning(partitions);
        }
        if (stopWhenRead) {
            endOffsets = consumer.endOffsets(partitions).entrySet().stream()
                    .filter(entry -> entry.getValue() > 0)
                    .collect(Collectors.toMap(entry -> entry.getKey().partition(), Map.Entry::getValue));

        }
    }

    @Override
    protected void doCommit() {
        consumer.commitSync();
    }

    @Override
    protected void doUnsubscribe() {
        if (consumer != null) {
            consumer.unsubscribe();
            consumer.close();
        }
        if (statsService != null) {
            statsService.unregisterClientGroup(groupId);
        }
    }

    @Override
    public boolean isStopped() {
        return false;
    }
}
