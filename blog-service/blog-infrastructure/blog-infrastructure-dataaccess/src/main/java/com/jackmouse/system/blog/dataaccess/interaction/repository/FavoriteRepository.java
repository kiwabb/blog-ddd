package com.jackmouse.system.blog.dataaccess.interaction.repository;

import com.jackmouse.system.blog.dataaccess.interaction.entity.FavoriteEntity;
import com.jackmouse.system.blog.dataaccess.interaction.entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * @ClassName LikeRepository
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/21 13:13
 * @Version 1.0
 **/
public interface FavoriteRepository extends JpaRepository<FavoriteEntity, UUID> {
    Optional<FavoriteEntity> findByTargetIdAndUserId(UUID targetId, Long userId);
}
