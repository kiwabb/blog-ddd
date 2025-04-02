package com.jackmouse.system.iot.entry;


import com.jackmouse.system.iot.DataType;

import java.util.Objects;
import java.util.Optional;

/**
 * @ClassName StringDataEntry
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/1 14:46
 * @Version 1.0
 **/
public class StringDataEntry extends BasicKvEntry{
    private static final long serialVersionUID = 1L;

    private final String value;
    public StringDataEntry(String key, String value) {
        super(key);
        this.value = value;
    }

    @Override
    public DataType getDataType() {
        return DataType.STRING;
    }

    @Override
    public String getValueAsString() {
        return value;
    }
    @Override
    public Optional<String> getStrValue() {
        return Optional.ofNullable(value);
    }


    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof StringDataEntry that))
            return false;
        if (!super.equals(o))
            return false;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), value);
    }

    @Override
    public String toString() {
        return "StringDataEntry{" + "value='" + value + '\'' + "} " + super.toString();
    }

}
