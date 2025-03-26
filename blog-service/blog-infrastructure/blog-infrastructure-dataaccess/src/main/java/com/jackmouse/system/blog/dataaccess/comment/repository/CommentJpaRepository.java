package com.jackmouse.system.blog.dataaccess.comment.repository;

import com.jackmouse.system.blog.dataaccess.comment.entity.CommentEntity;
import com.jackmouse.system.blog.domain.comment.valueobject.CommentId;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @ClassName CommentJpaRepository
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/25 17:05
 * @Version 1.0
 **/
@Repository
public interface CommentJpaRepository extends JpaRepository<CommentEntity, UUID>, JpaSpecificationExecutor<CommentEntity> {

    @Query(value = """
        SELECT * FROM (
            SELECT c.*, ROW_NUMBER() OVER (PARTITION BY parent_comment_id ORDER BY created_at) AS row_num
            FROM "blog".comment c
            WHERE c.parent_comment_id IN (:parentIds)
        ) sub
        WHERE sub.row_num <= :previewReplyCount
        """, nativeQuery = true)
    List<CommentEntity> findLimitedByParentIds(List<UUID> parentIds, int previewReplyCount);

    @Modifying
    @Query(value = """
            update  "blog".comment set is_deleted = true WHERE path <@ :path
            """, nativeQuery = true)
    void deleteAllChildren(String path);
}
