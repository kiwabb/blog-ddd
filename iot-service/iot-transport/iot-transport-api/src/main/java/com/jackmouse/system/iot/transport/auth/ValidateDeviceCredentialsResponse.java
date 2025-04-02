package com.jackmouse.system.iot.transport.auth;

import com.jackmouse.system.iot.device.entity.DeviceProfile;
import lombok.Builder;
import lombok.Data;

/**
 * @ClassName ValidateDeviceCredentialsResponse
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/1 14:58
 * @Version 1.0
 **/
@Data
@Builder
public class ValidateDeviceCredentialsResponse {
    private final TransportDeviceInfo deviceInfo;
    private final DeviceProfile deviceProfile;
    private final String credentials;

    public boolean hasDeviceInfo() {
        return deviceInfo != null;
    }
}
