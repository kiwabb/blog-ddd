package com.jackmouse.system.iot.divice.dto.query;

import com.jackmouse.system.iot.device.entity.Device;
import com.jackmouse.system.iot.device.entity.DeviceProfile;
import com.jackmouse.system.iot.device.valueobject.DeviceProfileProvisionType;
import com.jackmouse.system.iot.device.valueobject.DeviceProfileType;
import com.jackmouse.system.iot.device.valueobject.DeviceTransportType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * @ClassName DeviceProfileResponse
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/28 13:56
 * @Version 1.0
 **/
@Getter
@Builder
@AllArgsConstructor
public class DeviceResponse {
    private final UUID id;
    private final String name;
    private final String type;
    private final String label;
    private final UUID deviceProfileId;
    private final Long version;
    private final UUID customerId;
    private final ZonedDateTime createdAt;
    public static DeviceResponse from(Device device) {

        return DeviceResponse.builder()
                .id(device.getId().getValue())
                .name(device.getName())
                .type(device.getType())
                .label(device.getLabel())
                .deviceProfileId(device.getDeviceProfileId().getValue())
                .version(device.getVersion())
                .customerId(device.getCustomerId().getValue())
                .createdAt(device.getCreatedAt())
                .build();
    }


}
