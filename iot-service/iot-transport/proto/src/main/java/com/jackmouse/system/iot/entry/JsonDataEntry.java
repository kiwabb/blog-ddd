package com.jackmouse.system.iot.entry;


import com.jackmouse.system.iot.DataType;

import java.util.Objects;
import java.util.Optional;

/**
 * @ClassName JsonDataEntry
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/1 14:49
 * @Version 1.0
 **/
public class JsonDataEntry extends BasicKvEntry{
    private final String value;

    public JsonDataEntry(String key, String value) {
        super(key);
        this.value = value;
    }

    @Override
    public DataType getDataType() {
        return DataType.JSON;
    }

    @Override
    public Optional<String> getJsonValue() {
        return Optional.ofNullable(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JsonDataEntry that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(value, that.value);
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), value);
    }

    @Override
    public String toString() {
        return "JsonDataEntry{" +
                "value=" + value +
                "} " + super.toString();
    }

    @Override
    public String getValueAsString() {
        return value;
    }
}
