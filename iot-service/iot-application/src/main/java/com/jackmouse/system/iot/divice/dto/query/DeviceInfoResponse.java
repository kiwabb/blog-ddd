package com.jackmouse.system.iot.divice.dto.query;

import com.jackmouse.system.iot.device.valueobject.DeviceInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * @ClassName DeviceInfoResponse
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/31 13:21
 * @Version 1.0
 **/
@Getter
@Builder
@AllArgsConstructor
public class DeviceInfoResponse{
    private DeviceResponse device;
    private String customerTitle;
    private boolean customerIsPublic;
    private String deviceProfileName;
    private boolean active;

    public static DeviceInfoResponse from(DeviceInfo deviceInfo) {
        return DeviceInfoResponse.builder()
                .device(DeviceResponse.from(deviceInfo.getDevice()))
                .customerTitle(deviceInfo.getCustomerTitle())
                .customerIsPublic(deviceInfo.isCustomerIsPublic())
                .deviceProfileName(deviceInfo.getDeviceProfileName())
                .active(deviceInfo.isActive())
                .build();
    }
}
