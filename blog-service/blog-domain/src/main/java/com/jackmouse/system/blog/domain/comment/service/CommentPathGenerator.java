package com.jackmouse.system.blog.domain.comment.service;

import com.jackmouse.system.blog.domain.valueobject.Path;

import java.util.Optional;
import java.util.UUID;

/**
 * @ClassName CommentPathGenerator
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/26 09:12
 * @Version 1.0
 **/
public interface CommentPathGenerator {
    Path generatePath(Optional<UUID> parentCommentId);
}
