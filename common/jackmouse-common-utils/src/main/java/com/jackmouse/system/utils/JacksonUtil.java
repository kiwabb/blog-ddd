package com.jackmouse.system.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

import java.io.IOException;

/**
 * @ClassName JacksonUtil
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/28 13:19
 * @Version 1.0
 **/
public class JacksonUtil {

    public static final ObjectMapper OBJECT_MAPPER = JsonMapper.builder()
            .addModule(new Jdk8Module())
            .build();

    public static JsonNode toJsonNode(String value) {
        return toJsonNode(value, OBJECT_MAPPER);
    }

    public static String toString(Object value) {
        try {
            return value != null ? OBJECT_MAPPER.writeValueAsString(value) : null;
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("The given Json object value cannot be transformed to a String: " + value, e);
        }
    }

    public static JsonNode toJsonNode(String value, ObjectMapper mapper) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            return mapper.readTree(value);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
