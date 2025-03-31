package com.jackmouse.system.iot.device.repository;

import com.jackmouse.system.blog.domain.valueobject.PageResult;
import com.jackmouse.system.iot.device.entity.DeviceProfile;
import com.jackmouse.system.iot.device.specification.query.DeviceProfileQuerySpec;
import com.jackmouse.system.iot.device.valueobject.DeviceProfileId;

import java.util.Optional;

/**
 * @ClassName DeviceProfileRepository
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/28 14:03
 * @Version 1.0
 **/
public interface DeviceProfileRepository {
    PageResult<DeviceProfile> findDeviceProfiles(DeviceProfileQuerySpec spec);

    Optional<DeviceProfile> findDeviceProfileById(DeviceProfileId deviceProfileId);
}
