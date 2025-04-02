package com.jackmouse.system.iot.entry;


import com.jackmouse.system.iot.DataType;

import java.util.Objects;
import java.util.Optional;

/**
 * @ClassName LongDataEntry
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/1 14:50
 * @Version 1.0
 **/
public class LongDataEntry extends BasicKvEntry{
    private final Long value;

    public LongDataEntry(String key, Long value) {
        super(key);
        this.value = value;
    }

    @Override
    public DataType getDataType() {
        return DataType.LONG;
    }

    @Override
    public Optional<Long> getLongValue() {
        return Optional.ofNullable(value);
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LongDataEntry)) return false;
        if (!super.equals(o)) return false;
        LongDataEntry that = (LongDataEntry) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), value);
    }

    @Override
    public String toString() {
        return "LongDataEntry{" +
                "value=" + value +
                "} " + super.toString();
    }

    @Override
    public String getValueAsString() {
        return Long.toString(value);
    }
}
