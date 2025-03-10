package com.jackmouse.system.blog.dataaccess.article.repoository;

import com.jackmouse.system.blog.dataaccess.article.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName CategoryJpaRepository
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/10 08:38
 * @Version 1.0
 **/
@Repository
public interface CategoryJpaRepository extends JpaRepository<CategoryEntity, Long> {
    List<CategoryEntity> findTop4ByOrderBySort();
}
