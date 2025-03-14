package com.jackmouse.system.blog.domain.exception;

/**
 * @ClassName BlogDomainException
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/7 11:14
 * @Version 1.0
 **/
public class BlogDomainException extends RuntimeException{
    public BlogDomainException(String message) {
        super(message);
    }
    public BlogDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
