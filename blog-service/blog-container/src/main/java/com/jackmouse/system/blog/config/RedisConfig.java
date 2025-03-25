package com.jackmouse.system.blog.config;

import com.jackmouse.system.redis.config.RedisAutoConfig;
import com.jackmouse.system.redis.config.RedissonAutoConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @ClassName RedisConfig
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/21 13:34
 * @Version 1.0
 **/
@Configuration
@Import({RedisAutoConfig.class, RedissonAutoConfig.class})
public class RedisConfig {

}
