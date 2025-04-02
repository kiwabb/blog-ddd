package com.jackmouse.system.iot.entry;

import java.util.Objects;
import java.util.Optional;

/**
 * @ClassName BasicKvEntry
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/1 14:44
 * @Version 1.0
 **/
public abstract class BasicKvEntry implements KvEntry{
    private final String key;
    protected BasicKvEntry(String key) {
        this.key = key;
    }
    @Override
    public String getKey() {
        return key;
    }
    @Override
    public Optional<String> getStrValue() {
        return Optional.empty();
    }

    @Override
    public Optional<Long> getLongValue() {
        return Optional.empty();
    }

    @Override
    public Optional<Boolean> getBooleanValue() {
        return Optional.empty();
    }

    @Override
    public Optional<Double> getDoubleValue() {
        return Optional.empty();
    }

    @Override
    public Optional<String> getJsonValue() {
        return Optional.empty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BasicKvEntry that)) return false;
        return Objects.equals(key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

    @Override
    public String toString() {
        return "BasicKvEntry{" +
                "key='" + key + '\'' +
                '}';
    }
}
