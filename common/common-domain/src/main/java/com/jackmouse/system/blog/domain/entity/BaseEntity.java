package com.jackmouse.system.blog.domain.entity;

import java.util.Objects;

/**
 * @ClassName BaseEntity
 * @Description 基础实体 重写equals方法，具有唯一标识符（ID）
 * @Author zhoujiaangyao
 * @Date 2025/3/7 10:30
 * @Version 1.0
 **/
public class BaseEntity<ID> {
    private ID id;

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity<?> that = (BaseEntity<?>) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
