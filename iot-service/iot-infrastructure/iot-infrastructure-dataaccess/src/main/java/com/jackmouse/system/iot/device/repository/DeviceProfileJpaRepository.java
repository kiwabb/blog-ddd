package com.jackmouse.system.iot.device.repository;

import com.jackmouse.system.entity.ToData;
import com.jackmouse.system.iot.device.entity.DeviceProfile;
import com.jackmouse.system.iot.device.entity.DeviceProfileEntity;
import com.jackmouse.system.iot.device.specification.query.DeviceProfileQuerySpec;
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
public interface DeviceProfileJpaRepository extends JpaRepository<DeviceProfileEntity, UUID>{
    Page<DeviceProfileEntity> findByNameLike(String name, Pageable pageable);
}
