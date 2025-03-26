package com.jackmouse.system.blog.domain.comment.service.impl;

import com.jackmouse.system.blog.domain.comment.service.CommentPathGenerator;
import com.jackmouse.system.blog.domain.valueobject.Path;

import java.util.Optional;
import java.util.UUID;

/**
 * @ClassName CommentPathGeneratorImpl
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/26 09:32
 * @Version 1.0
 **/
public class CommentPathGeneratorImpl implements CommentPathGenerator {
    @Override
    public Path generatePath(Optional<UUID> parentCommentId) {
        return null;
    }
}
