package com.jackmouse.system.iot;

import lombok.Getter;

/**
 * @ClassName DataType
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/1 14:39
 * @Version 1.0
 **/
@Getter
public enum DataType {
    BOOLEAN(0),
    LONG(1),
    DOUBLE(2),
    STRING(3),
    JSON(4);
    private final int protoNumber;
    DataType(int protoNumber) {
        this.protoNumber = protoNumber;
    }
}
