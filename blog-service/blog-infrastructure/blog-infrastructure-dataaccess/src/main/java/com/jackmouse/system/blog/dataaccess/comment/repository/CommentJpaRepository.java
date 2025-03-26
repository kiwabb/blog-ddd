package com.jackmouse.system.blog.dataaccess.comment.repository;

import com.jackmouse.system.blog.dataaccess.comment.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @ClassName CommentJpaRepository
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/25 17:05
 * @Version 1.0
 **/
@Repository
public interface CommentJpaRepository extends JpaRepository<CommentEntity, UUID> {
}
