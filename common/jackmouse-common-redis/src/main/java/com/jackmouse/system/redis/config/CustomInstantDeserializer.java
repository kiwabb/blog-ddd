package com.jackmouse.system.redis.config;

import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @ClassName CustomInstantDeserializer
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/21 14:24
 * @Version 1.0
 **/
public class CustomInstantDeserializer extends InstantDeserializer<Instant> {
    public CustomInstantDeserializer(InstantDeserializer<Instant> base, DateTimeFormatter f) {
        super(base, f);
    }
}
