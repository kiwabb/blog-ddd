package com.jackmouse.system.redis.config;

import com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializer;

import java.time.format.DateTimeFormatter;

/**
 * @ClassName CustomInstantSerializer
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/21 14:24
 * @Version 1.0
 **/
public class CustomInstantSerializer extends InstantSerializer {
    public CustomInstantSerializer(InstantSerializer base, Boolean useTimestamp, Boolean useNanoseconds,
                                   DateTimeFormatter formatter) {
        super(base, useTimestamp, useNanoseconds, formatter);
    }
}
