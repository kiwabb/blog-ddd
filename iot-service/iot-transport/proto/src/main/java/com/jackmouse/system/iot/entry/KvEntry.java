package com.jackmouse.system.iot.entry;


import com.jackmouse.system.iot.DataType;

import java.io.Serializable;
import java.util.Optional;

/**
 * @ClassName KvEntry
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/1 14:39
 * @Version 1.0
 **/
public interface KvEntry extends Serializable {
    String getKey();

    DataType getDataType();

    Optional<String> getStrValue();

    Optional<Long> getLongValue();

    Optional<Boolean> getBooleanValue();

    Optional<Double> getDoubleValue();

    Optional<String> getJsonValue();

    String getValueAsString();

    Object getValue();
}
