package com.jackmouse.system.blog.domain.valueobject;

import java.util.Objects;

/**
 * @ClassName BaseId
 * @Description 值对象抽象类
 * @Author zhoujiaangyao
 * @Date 2025/3/7 10:25
 * @Version 1.0
 **/
public abstract class BaseId<T> {
    private final T value;

    protected BaseId(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseId<?> baseId = (BaseId<?>) o;
        return Objects.equals(value, baseId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
