package com.jackmouse.system.system.dataaccess.repositooy;

import com.jackmouse.system.system.dataaccess.entity.SysMenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @ClassName MenuJpaRepository
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/18 10:17
 * @Version 1.0
 **/
@Repository
public interface MenuJpaRepository extends JpaRepository<SysMenuEntity, Long>,
        JpaSpecificationExecutor<SysMenuEntity> {
}
