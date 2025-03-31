package com.jackmouse.system.iot.device;

import com.jackmouse.system.iot.device.entity.Device;
import com.jackmouse.system.iot.device.entity.DeviceProfile;
import com.jackmouse.system.iot.device.repository.DeviceProfileRepository;

/**
 * @ClassName DeviceDomainService
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/31 16:13
 * @Version 1.0
 **/
public class DeviceDomainService {
    public Device syncDeviceWithProfile(Device device, DeviceProfile profile) {
        device.updateType(profile.getName());
        device.syncConfiguration(profile);
        return device;
    }
}
