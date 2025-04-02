package com.jackmouse.system.iot.device;

import com.jackmouse.system.iot.device.specification.DeviceTransportConfiguration;
import com.jackmouse.system.iot.device.valueobject.DefaultDeviceTransportConfiguration;
import com.jackmouse.system.blog.domain.valueobject.DeviceTransportType;

/**
 * @ClassName DeviceTransportConfigurationFactory
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/31 16:49
 * @Version 1.0
 **/
public class DeviceTransportConfigurationFactory {
    public static DeviceTransportConfiguration create(DeviceTransportType transportType) {
        if (transportType == DeviceTransportType.DEFAULT) {
            return new  DefaultDeviceTransportConfiguration();
        }
        return null;
    }
}
