package com.jackmouse.system.blog.domain.exception;

import java.util.UUID;

/**
 * @ClassName DomianException
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/31 15:04
 * @Version 1.0
 **/
public class DomainException extends RuntimeException{
    public DomainException(String message) {
        super(message);
    }
    public DomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
