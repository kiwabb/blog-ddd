package com.jackmouse.system.iot.device.specification;

import com.jackmouse.system.iot.device.valueobject.DeviceTransportType;

/**
 * @ClassName DeviceTransportConfiguration
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/31 15:18
 * @Version 1.0
 **/
public interface DeviceTransportConfiguration {
    DeviceTransportType getType();
}
