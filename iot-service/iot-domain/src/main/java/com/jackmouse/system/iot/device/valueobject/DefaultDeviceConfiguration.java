package com.jackmouse.system.iot.device.valueobject;

import com.jackmouse.system.iot.device.specification.DeviceConfiguration;

/**
 * @ClassName DefaultDeviceConfiguration
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/31 15:21
 * @Version 1.0
 **/
public class DefaultDeviceConfiguration implements DeviceConfiguration {
    @Override
    public DeviceProfileType getType() {
        return DeviceProfileType.DEFAULT;
    }
}
