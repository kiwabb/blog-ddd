package com.jackmouse.system.iot.message.queue;

import lombok.Builder;
import lombok.Getter;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @ClassName TopicPartitionInfo
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/2 08:52
 * @Version 1.0
 **/
public class TopicPartitionInfo {
    @Getter
    private final String topic;
    private final Object tenantId;
    private final Integer partition;
    private final boolean useInternalPartition;
    @Getter
    private final String fullTopicName;
    private final boolean myPartition;

    @Builder
    public TopicPartitionInfo(String topic, Object tenantId, Integer partition, boolean useInternalPartition, boolean myPartition) {
        this.topic = topic;
        this.tenantId = tenantId;
        this.partition = partition;
        this.useInternalPartition = useInternalPartition;
        this.myPartition = myPartition;
        String tmp = topic;
        if (partition != null && !useInternalPartition) {
            tmp += "." + partition;
        }
        this.fullTopicName = tmp;
    }

    public TopicPartitionInfo(String topic, Object tenantId, Integer partition, boolean myPartition) {
        this(topic, tenantId, partition, false, myPartition);
    }
    public TopicPartitionInfo newByTopic(String topic) {
        return new TopicPartitionInfo(topic, this.tenantId, this.partition, this.useInternalPartition, this.myPartition);
    }

    public Optional<Object> getTenantId() {
        return Optional.ofNullable(tenantId);
    }

    public Optional<Integer> getPartition() {
        return Optional.ofNullable(partition);
    }

    public TopicPartitionInfo withTopic(String topic) {
        return new TopicPartitionInfo(topic, this.tenantId, this.partition, this.useInternalPartition, this.myPartition);
    }

    public static Set<TopicPartitionInfo> withTopic(Set<TopicPartitionInfo> partitions, String topic) {
        return partitions.stream().map(tpi -> tpi.withTopic(topic)).collect(Collectors.toSet());
    }
    public TopicPartitionInfo withUseInternalPartition(boolean useInternalPartition) {
        return new TopicPartitionInfo(this.topic, this.tenantId, this.partition, useInternalPartition, this.myPartition);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TopicPartitionInfo that = (TopicPartitionInfo) o;
        return topic.equals(that.topic) &&
                Objects.equals(tenantId, that.tenantId) &&
                Objects.equals(partition, that.partition) &&
                fullTopicName.equals(that.fullTopicName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullTopicName, partition);
    }

    @Override
    public String toString() {
        String str = fullTopicName;
        if (useInternalPartition) {
            str += "[" + partition + "]";
        }
        return str;
    }

}
