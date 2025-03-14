package com.jackmouse.system.blog.domain.exception;

/**
 * @ClassName BlogNotFoundException
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/14 10:05
 * @Version 1.0
 **/
public class BlogNotFoundException extends RuntimeException{
    public BlogNotFoundException(String message) {
        super(message);
    }
    public BlogNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
