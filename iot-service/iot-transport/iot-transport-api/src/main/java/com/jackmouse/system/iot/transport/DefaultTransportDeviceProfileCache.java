package com.jackmouse.system.iot.transport;

import com.jackmouse.server.gen.transport.TransportProtos;
import com.jackmouse.system.iot.device.entity.DeviceProfile;
import com.jackmouse.system.iot.device.valueobject.DeviceProfileId;
import com.jackmouse.system.iot.util.ProtoUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @ClassName DefaultTransportDeviceProfileCache
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/2 14:12
 * @Version 1.0
 **/
@Slf4j
@Component
public class DefaultTransportDeviceProfileCache implements TransportDeviceProfileCache{

    private final ConcurrentMap<DeviceProfileId, DeviceProfile> deviceProfiles = new ConcurrentHashMap<>();


    @Override
    public DeviceProfile getOrCreate(DeviceProfileId deviceProfileId, TransportProtos.DeviceProfileProto proto) {
        DeviceProfile profile = deviceProfiles.get(deviceProfileId);
        if (profile == null) {
            profile = ProtoUtils.fromProto(proto);
            deviceProfiles.put(deviceProfileId, profile);
        }
        return profile;
    }
}
