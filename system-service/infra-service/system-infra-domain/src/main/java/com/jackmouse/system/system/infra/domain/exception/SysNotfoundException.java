package com.jackmouse.system.system.infra.domain.exception;

/**
 * @ClassName SysNotfoundException
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/17 13:59
 * @Version 1.0
 **/
public class SysNotfoundException extends RuntimeException{
    public SysNotfoundException(String message) {
        super(message);
    }
    public SysNotfoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
