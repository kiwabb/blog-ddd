package com.jackmouse.system.iot.queue.util;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @ClassName PropertyUtils
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/3 13:41
 * @Version 1.0
 **/
public class PropertyUtils {
    public static Map<String, String> getProps(String properties) {
        Map<String, String> configs = new HashMap<>();
        if (StringUtils.isNotEmpty(properties)) {
            for (String property : properties.split(";")) {
                if (StringUtils.isNotEmpty(property)) {
                    int delimiterPosition = property.indexOf(":");
                    String key = property.substring(0, delimiterPosition);
                    String value = property.substring(delimiterPosition + 1);
                    configs.put(key, value);
                }
            }
        }
        return configs;
    }

    public static Map<String, String> getProps(Map<String, String> defaultProperties, String propertiesStr) {
        return getProps(defaultProperties, propertiesStr, PropertyUtils::getProps);
    }

    public static Map<String, String> getProps(Map<String, String> defaultProperties, String propertiesStr, Function<String, Map<String, String>> parser) {
        Map<String, String> properties = new HashMap<>(defaultProperties);
        if (StringUtils.isNotBlank(propertiesStr)) {
            properties.putAll(parser.apply(propertiesStr));
        }
        return properties;
    }
}
