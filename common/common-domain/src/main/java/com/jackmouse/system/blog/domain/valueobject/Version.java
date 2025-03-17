package com.jackmouse.system.blog.domain.valueobject;

/**
 * @ClassName Version
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/17 11:29
 * @Version 1.0
 **/
public class Version {
    private final long value;

    public Version(long major) {
        this.value = major;
    }

    public long getValue() {
        return value;
    }

}
