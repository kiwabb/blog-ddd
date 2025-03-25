package com.jackmouse.system.redis.config;

import com.jackmouse.system.utils.ObjectUtil;
import com.jackmouse.system.utils.RedisKeyUtil;
import org.redisson.Redisson;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

import static com.jackmouse.system.constant.StringConstant.RISK;

/**
 * @ClassName RedissonAutoConfig
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/21 14:32
 * @Version 1.0
 **/
@AutoConfiguration
@ConditionalOnClass(Redisson.class)
@EnableConfigurationProperties(RedisProperties.class)
public class RedissonAutoConfig {
    /**
     * Redis未加密连接.
     */
    private static final String REDIS_PROTOCOL_PREFIX = "redis://";

    /**
     * Redis加密连接.
     */
    private static final String REDISS_PROTOCOL_PREFIX = "rediss://";

    @Bean
    public RBloomFilter<String> bloomFilter(RedissonClient redisson) {
        RBloomFilter<String> bloomFilter = redisson.getBloomFilter(RedisKeyUtil.getBloomFilterKey());
        bloomFilter.tryInit(10000, 0.01);
        return bloomFilter;
    }

    @Bean(destroyMethod = "shutdown")
    @ConditionalOnMissingBean(RedissonClient.class)
    public RedissonClient redisClient(RedisProperties properties) {
        Config config = new Config();
        int timeout = (int) properties.getTimeout().toMillis();
        int connectTimeout = (int) properties.getConnectTimeout().toMillis();
        boolean isSsl = properties.getSsl().isEnabled();
        if (ObjectUtil.isNotNull(properties.getSentinel())) {
            config.useSentinelServers()
                    .setMasterName(properties.getSentinel().getMaster())
                    .addSentinelAddress(convertNodes(isSsl, properties.getSentinel().getNodes()))
                    .setDatabase(properties.getDatabase())
                    .setTimeout(timeout)
                    .setConnectTimeout(connectTimeout)
                    .setPassword(properties.getPassword());
        }
        else if (ObjectUtil.isNotNull(properties.getCluster())) {
            config.useClusterServers()
                    .addNodeAddress(convertNodes(isSsl, properties.getCluster().getNodes()))
                    .setPassword(properties.getPassword())
                    .setTimeout(timeout)
                    .setConnectTimeout(connectTimeout);
        }
        else {
            config.useSingleServer()
                    .setAddress(convertAddress(isSsl, properties.getHost(), properties.getPort()))
                    .setDatabase(properties.getDatabase())
                    .setPassword(properties.getPassword())
                    .setConnectTimeout(connectTimeout)
                    .setTimeout(timeout);
        }
        // 使用json序列化方式
        config.setCodec(GlobalJsonJacksonCodec.INSTANCE);
        return Redisson.create(config);
    }
    private String[] convertNodes(boolean isSsl, List<String> nodeList) {
        List<String> nodes = new ArrayList<>(nodeList.size());
        for (String node : nodeList) {
            if (node.startsWith(REDISS_PROTOCOL_PREFIX) || node.startsWith(REDIS_PROTOCOL_PREFIX)) {
                nodes.add(node);
            }
            else {
                nodes.add(getProtocolPrefix(isSsl) + node);
            }
        }
        return nodes.toArray(String[]::new);
    }
    private String getProtocolPrefix(boolean isSsl) {
        return isSsl ? REDISS_PROTOCOL_PREFIX : REDIS_PROTOCOL_PREFIX;
    }
    private String convertAddress(boolean isSsl, String host, int port) {
        return getProtocolPrefix(isSsl) + host + RISK + port;
    }
}
