package com.jackmouse.system.iot.device.valueobject;

import com.jackmouse.system.blog.domain.valueobject.DeviceTransportType;
import com.jackmouse.system.iot.device.specification.DeviceTransportConfiguration;

/**
 * @ClassName DefaultDeviceTransportConfiguration
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/31 15:34
 * @Version 1.0
 **/
public class DefaultDeviceTransportConfiguration implements DeviceTransportConfiguration {
    @Override
    public DeviceTransportType getType() {
        return DeviceTransportType.DEFAULT;
    }
}
