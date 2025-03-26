package com.jackmouse.system.blog.domain.valueobject;

/**
 * @ClassName Depth
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/26 11:23
 * @Version 1.0
 **/
public record Depth(Integer value) {
    public static final Depth ZERO = new Depth(0);
    public Depth increase() {
        return new Depth(value + 1);
    }
}
