package com.jackmouse.system.blog.domain.comment.valueobject;

import com.jackmouse.system.blog.domain.valueobject.BaseId;

import java.util.UUID;

/**
 * @ClassName CommentId
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/26 08:45
 * @Version 1.0
 **/
public class CommentId extends BaseId<UUID> {
    public CommentId(UUID value) {
        super(value);
    }
}
