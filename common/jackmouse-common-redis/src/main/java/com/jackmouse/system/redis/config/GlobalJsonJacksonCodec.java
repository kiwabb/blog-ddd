package com.jackmouse.system.redis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.OffsetTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.OffsetDateTimeSerializer;
import com.jackmouse.system.utils.DataUtil;
import org.redisson.codec.JsonJacksonCodec;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import static com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping.NON_FINAL;

/**
 * @ClassName GlobalJsonJacksonCodec
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/21 13:58
 * @Version 1.0
 **/
public class GlobalJsonJacksonCodec extends JsonJacksonCodec {
    public static final GlobalJsonJacksonCodec INSTANCE = new GlobalJsonJacksonCodec();
    private GlobalJsonJacksonCodec() {
        super(objectMapper());
    }

    public static Jackson2JsonRedisSerializer<Object> getJsonRedisSerializer() {
        // Json序列化配置
        return new Jackson2JsonRedisSerializer<>(objectMapper(), Object.class);
    }

    public static StringRedisSerializer getStringRedisSerializer() {
        return new Md5DigestStringRedisSerializer(StandardCharsets.UTF_8);
    }
    private static ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        DateTimeFormatter dateTimeFormatter = DataUtil.getDateTimeFormatter(DataUtil.YYYY_B_MM_B_DD_HH_R_MM_R_SS);
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        // Long类型转String类型
        javaTimeModule.addSerializer(Long.class, ToStringSerializer.instance);
        javaTimeModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        // LocalDateTime
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter));
        // Instant
        javaTimeModule.addSerializer(Instant.class, new CustomInstantSerializer(InstantSerializer.INSTANCE, false,false, dateTimeFormatter));
        javaTimeModule.addDeserializer(Instant.class, new CustomInstantDeserializer(InstantDeserializer.INSTANT, dateTimeFormatter));
        objectMapper.registerModule(javaTimeModule);
        // 所有属性访问器（字段、getter和setter），将自动检测所有字段属性
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 对于所有非final类型，使用LaissezFaire子类型验证器来推断类型
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, NON_FINAL, JsonTypeInfo.As.PROPERTY);
        // 反序列化时，属性不存在的兼容处理
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 自动查找并注册相关模块
        objectMapper.findAndRegisterModules();
        return objectMapper;
    }
    public static class Md5DigestStringRedisSerializer extends StringRedisSerializer {

        public Md5DigestStringRedisSerializer(Charset charset) {
            super(charset);
        }

        @Override
        public byte[] serialize(String value) {
            Assert.notNull(value, "Cannot serialize null");
            return super.serialize(DigestUtils.md5DigestAsHex(value.getBytes(StandardCharsets.UTF_8)));
        }

    }
}
