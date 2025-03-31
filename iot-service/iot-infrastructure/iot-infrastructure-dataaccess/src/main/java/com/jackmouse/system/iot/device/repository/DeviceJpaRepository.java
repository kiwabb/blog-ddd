package com.jackmouse.system.iot.device.repository;

import com.jackmouse.system.iot.device.entity.DeviceEntity;
import com.jackmouse.system.iot.device.entity.DeviceProfileEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @ClassName DeviceProfileJpaRepository
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/28 14:36
 * @Version 1.0
 **/
@Repository
public interface DeviceJpaRepository extends JpaRepository<DeviceEntity, UUID>{
    Page<DeviceEntity> findByNameLike(String name, Pageable pageable);
}
