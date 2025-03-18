package com.jackmouse.system.system.dataaccess.repositooy;

import com.jackmouse.system.system.dataaccess.entity.SysUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @ClassName UserJpaRepository
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/14 16:27
 * @Version 1.0
 **/
@Repository
public interface UserJpaRepository extends JpaRepository<SysUserEntity, Long>, JpaSpecificationExecutor<SysUserEntity> {
}
