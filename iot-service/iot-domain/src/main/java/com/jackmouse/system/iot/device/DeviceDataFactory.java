package com.jackmouse.system.iot.device;

import com.jackmouse.system.iot.device.entity.DeviceProfile;
import com.jackmouse.system.iot.device.valueobject.DeviceData;

/**
 * @ClassName DeviceDataFactory
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/31 16:40
 * @Version 1.0
 **/
public class DeviceDataFactory {
    public static DeviceData createBaseOnProfile(DeviceProfile profile, DeviceData existingData) {
        DeviceData data =  existingData != null? existingData: new DeviceData();
        if (shouldUpdateConfiguration(profile, data)) {
            data.setConfiguration(DeviceConfigurationFactory.create(profile.getType()));
        }
        if (shouldUpdateTransportConfiguration(profile, data)) {
            data.setTransportConfiguration(DeviceTransportConfigurationFactory.create(profile.getTransportType()));
        }
        return data;
    }

    private static boolean shouldUpdateTransportConfiguration(DeviceProfile profile, DeviceData data) {
        return (data.getConfiguration() != null && !profile.getType().equals(data.getConfiguration().getType()));
    }

    private static boolean shouldUpdateConfiguration(DeviceProfile profile, DeviceData data) {
        return (data.getTransportConfiguration() != null && !profile.getTransportType().equals(data.getTransportConfiguration().getType()));
    }
}
