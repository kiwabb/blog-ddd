package com.jackmouse.system.iot.queue.kafka;

import com.jackmouse.system.iot.queue.util.PropertyUtils;
import lombok.Data;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @ClassName TbKafkaSettings
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/3 13:17
 * @Version 1.0
 **/
@Data
@Slf4j
@ConditionalOnProperty(prefix = "queue", name = "type", havingValue = "kafka")
@ConfigurationProperties(prefix = "queue.kafka")
@Component
public class JmKafkaSettings {
    private String bootstrapServers;
    private boolean sslEnabled;
    private String sslTruststoreLocation;
    private String sslTruststorePassword;
    private String sslKeystoreLocation;
    private String sslKeystorePassword;
    private String sslKeyPassword;
    private String acks;
    private int retries;
    private String compressionType;
    private String batchSize;
    private long lingerMs;
    private int maxRequestSize;
    private int maxInFlightRequestsPerConnection;
    private long bufferMemory;
    private short replicationFactor;
    private int maxPollRecords;
    private int maxPollIntervalMs;
    private int maxPartitionFetchBytes;
    private int fetchMaxBytes;
    private int requestTimeoutMs;
    private int sessionTimeoutMs;
    private String autoOffsetReset;
    private boolean useConfluent;
    private String sslAlgorithm;
    private String saslMechanism;
    private String saslConfig;
    private  String securityProtocol;
    private String otherInline;

    private static final List<String> DYNAMIC_TOPICS = List.of("jm_edge_event.notifications");
    private volatile AdminClient adminClient;

    @Setter
    private Map<String, List<JmProperty>> consumerPropertiesPerTopic = Collections.emptyMap();


    public Properties toProducerProperties() {
        Properties props = toProps();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.RETRIES_CONFIG, retries);
        props.put(ProducerConfig.ACKS_CONFIG, acks);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
        props.put(ProducerConfig.LINGER_MS_CONFIG, lingerMs);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class);
        props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, compressionType);
        props.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, maxRequestSize);
        props.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, maxInFlightRequestsPerConnection);
        return props;
    }

    Properties toProps() {
        Properties props = new Properties();
        if (useConfluent) {
            props.put("ssl.endpoint.identification.algorithm", sslAlgorithm);
            props.put("sasl.mechanism", saslMechanism);
            props.put("sasl.jaas.config", saslConfig);
            props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, securityProtocol);
        }
        props.put(CommonClientConfigs.REBALANCE_TIMEOUT_MS_CONFIG, requestTimeoutMs);
        props.putAll(PropertyUtils.getProps(otherInline));
        configureSSL(props);
        return props;
    }
    protected Properties toAdminProps() {
        Properties props = toProps();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(AdminClientConfig.RETRIES_CONFIG, retries);
        return props;
    }

    public AdminClient getAdminClient() {
        if (adminClient == null) {
            synchronized (this) {
                if (adminClient == null) {
                    adminClient = AdminClient.create(toAdminProps());
                }
            }
        }
        return adminClient;
    }

    void configureSSL(Properties props) {
        if (sslEnabled) {
            props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SSL");
            props.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, sslTruststoreLocation);
            props.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, sslTruststorePassword);
            props.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, sslKeystoreLocation);
            props.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, sslKeystorePassword);
            props.put(SslConfigs.SSL_KEY_PASSWORD_CONFIG, sslKeyPassword);
        }
    }

    public Properties toConsumerProperties(String topic) {
        Properties props = toProps();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollRecords);
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeoutMs);
        props.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, maxPartitionFetchBytes);
        props.put(ConsumerConfig.FETCH_MAX_BYTES_CONFIG, fetchMaxBytes);
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, maxPollIntervalMs);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class);

        consumerPropertiesPerTopic
                .getOrDefault(topic, Collections.emptyList())
                .forEach(kv -> props.put(kv.getKey(), kv.getValue()));

        if (topic != null) {
            DYNAMIC_TOPICS.stream()
                    .filter(topic::startsWith)
                    .findFirst()
                    .ifPresent(prefix -> consumerPropertiesPerTopic.getOrDefault(prefix, Collections.emptyList())
                            .forEach(kv -> props.put(kv.getKey(), kv.getValue())));
        }
        return props;
    }
}
