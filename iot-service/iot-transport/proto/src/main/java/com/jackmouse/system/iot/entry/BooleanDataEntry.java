package com.jackmouse.system.iot.entry;


import com.jackmouse.system.iot.DataType;

import java.util.Objects;
import java.util.Optional;

/**
 * @ClassName BooleanDataEntry
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/1 14:48
 * @Version 1.0
 **/
public class BooleanDataEntry extends BasicKvEntry{
    private final Boolean value;

    public BooleanDataEntry(String key, Boolean value) {
        super(key);
        this.value = value;
    }

    @Override
    public DataType getDataType() {
        return DataType.BOOLEAN;
    }

    @Override
    public Optional<Boolean> getBooleanValue() {
        return Optional.ofNullable(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BooleanDataEntry that)) return false;
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
        return "BooleanDataEntry{" +
                "value=" + value +
                "} " + super.toString();
    }

    @Override
    public String getValueAsString() {
        return Boolean.toString(value);
    }
}
