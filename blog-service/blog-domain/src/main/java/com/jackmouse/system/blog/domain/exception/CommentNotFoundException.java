package com.jackmouse.system.blog.domain.exception;

import java.util.UUID;

/**
 * @ClassName CommentNotFoundException
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/26 16:11
 * @Version 1.0
 **/
public class CommentNotFoundException extends BlogNotFoundException{
    public CommentNotFoundException(UUID commentId) {
        super("评论不存在，评论ID：" + commentId);
    }
}
