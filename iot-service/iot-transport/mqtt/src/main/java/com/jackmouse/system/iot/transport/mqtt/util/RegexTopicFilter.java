package com.jackmouse.system.iot.transport.mqtt.util;

import java.util.regex.Pattern;

/**
 * @ClassName RegexTopicFilter
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/8 11:16
 * @Version 1.0
 **/
public class RegexTopicFilter implements MqttTopicFilter{
    private final Pattern regex;

    public RegexTopicFilter(String regex) {
        this.regex = Pattern.compile(regex);
    }

    @Override
    public boolean filter(String topic) {
        return regex.matcher(topic).matches();
    }
}
