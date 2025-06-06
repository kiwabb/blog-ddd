package com.jackmouse.system.system.dataaccess.repositooy;

import com.jackmouse.system.system.dataaccess.entity.SysRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @ClassName RoleJpaRepository
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/18 10:16
 * @Version 1.0
 **/
@Repository
public interface RoleJpaRepository extends JpaRepository<SysRoleEntity, Long>,
        JpaSpecificationExecutor<SysRoleEntity> {
}
