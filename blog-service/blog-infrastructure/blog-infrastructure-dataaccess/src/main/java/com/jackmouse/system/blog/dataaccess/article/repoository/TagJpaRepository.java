package com.jackmouse.system.blog.dataaccess.article.repoository;

import com.jackmouse.system.blog.dataaccess.article.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @ClassName TagJpaRepository
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/10 08:40
 * @Version 1.0
 **/
@Repository
public interface TagJpaRepository extends JpaRepository<TagEntity, Long> {
}
