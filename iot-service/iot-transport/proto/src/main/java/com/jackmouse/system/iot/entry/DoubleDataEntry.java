package com.jackmouse.system.iot.entry;


import com.jackmouse.system.iot.DataType;

import java.util.Objects;
import java.util.Optional;

/**
 * @ClassName DoubleDataEntry
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/1 14:50
 * @Version 1.0
 **/
public class DoubleDataEntry extends BasicKvEntry{
    private final Double value;

    public DoubleDataEntry(String key, Double value) {
        super(key);
        this.value = value;
    }

    @Override
    public DataType getDataType() {
        return DataType.DOUBLE;
    }

    @Override
    public Optional<Double> getDoubleValue() {
        return Optional.ofNullable(value);
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DoubleDataEntry that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), value);
    }

    @Override
    public String toString() {
        return "DoubleDataEntry{" +
                "value=" + value +
                "} " + super.toString();
    }

    @Override
    public String getValueAsString() {
        return Double.toString(value);
    }
}
