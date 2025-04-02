package com.jackmouse.system.iot.transport;

import com.jackmouse.server.gen.transport.TransportProtos;
import com.jackmouse.system.iot.device.entity.DeviceProfile;
import com.jackmouse.system.iot.device.valueobject.DeviceProfileId;

/**
 * @ClassName TransportDeviceProfileCache
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/1 20:51
 * @Version 1.0
 **/
public interface TransportDeviceProfileCache {
    DeviceProfile getOrCreate(DeviceProfileId deviceProfileId, TransportProtos.DeviceProfileProto proto);
}
