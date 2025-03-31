package com.jackmouse.system.iot.device.repository;

import com.jackmouse.system.blog.domain.valueobject.PageResult;
import com.jackmouse.system.iot.device.entity.Device;
import com.jackmouse.system.iot.device.entity.DeviceProfile;
import com.jackmouse.system.iot.device.specification.query.DeviceProfileQuerySpec;
import com.jackmouse.system.iot.device.specification.query.DeviceQuerySpec;
import com.jackmouse.system.iot.device.valueobject.DeviceId;
import com.jackmouse.system.iot.device.valueobject.DeviceInfo;

import java.util.Optional;

/**
 * @ClassName DeviceProfileRepository
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/28 14:03
 * @Version 1.0
 **/
public interface DeviceRepository {
    PageResult<Device> findDevices(DeviceQuerySpec spec);
    PageResult<DeviceInfo> findDeviceInfos(DeviceQuerySpec spec);

    Optional<Device> findById(DeviceId deviceId);

    Device saveAndFlush(Device device);
}
