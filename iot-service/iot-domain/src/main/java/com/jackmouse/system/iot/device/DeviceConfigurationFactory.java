package com.jackmouse.system.iot.device;

import com.jackmouse.system.iot.device.specification.DeviceConfiguration;
import com.jackmouse.system.iot.device.valueobject.DefaultDeviceConfiguration;
import com.jackmouse.system.iot.device.valueobject.DeviceProfileType;

/**
 * @ClassName DeviceConfigurationFactory
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/31 16:49
 * @Version 1.0
 **/
public class DeviceConfigurationFactory {
    public static DeviceConfiguration create(DeviceProfileType type) {
        if (type == DeviceProfileType.DEFAULT) {
            return new DefaultDeviceConfiguration();
        }
        return null;
    }
}
