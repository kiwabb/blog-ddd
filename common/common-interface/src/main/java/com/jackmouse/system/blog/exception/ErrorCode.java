package com.jackmouse.system.blog.exception;

import lombok.Getter;

/**
 * @ClassName ErrorEnum
 * @Description
 * @Author zhoujiaangyao
 * @Date 2024/11/27 16:04
 * @Version 1.0
 **/
@Getter
public class ErrorCode {

    private final Integer code;
    private final String msg;
    public ErrorCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
