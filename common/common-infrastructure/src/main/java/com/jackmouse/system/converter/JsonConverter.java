package com.jackmouse.system.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.jackmouse.system.utils.JacksonUtil;
import jakarta.persistence.AttributeConverter;

/**
 * @ClassName JsonConverter
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/28 13:16
 * @Version 1.0
 **/
public class JsonConverter implements AttributeConverter<JsonNode, String> {
    @Override
    public String convertToDatabaseColumn(JsonNode jsonNode) {
        return JacksonUtil.toString(jsonNode);
    }

    @Override
    public JsonNode convertToEntityAttribute(String s) {
        return JacksonUtil.toJsonNode(s);
    }
}
