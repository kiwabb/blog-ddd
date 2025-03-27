package com.jackmouse.system.system.dataaccess.repositooy;

import com.jackmouse.system.system.dataaccess.entity.SysRoleEntity;
import com.jackmouse.system.system.dataaccess.entity.SysRoleMenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName RoleJpaRepository
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/18 10:16
 * @Version 1.0
 **/
@Repository
public interface RoleMenuJpaRepository extends JpaRepository<SysRoleMenuEntity, Long>,
        JpaSpecificationExecutor<SysRoleMenuEntity> {

    List<SysRoleMenuEntity> findByRoleId(Long roleId);
}
