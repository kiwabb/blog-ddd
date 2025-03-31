package com.jackmouse.system.iot.divice.dto.save;

import com.fasterxml.jackson.databind.JsonNode;
import com.jackmouse.system.iot.device.entity.Device;
import com.jackmouse.system.iot.device.valueobject.CustomerId;
import com.jackmouse.system.iot.device.valueobject.DeviceId;
import com.jackmouse.system.iot.device.valueobject.DeviceProfileId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

/**
 * @ClassName SaveDeviceCommand
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/31 14:30
 * @Version 1.0
 **/
@Getter
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class SaveDeviceCommand {
    private UUID id;
    private transient JsonNode additionalInfo;
    private UUID customerId;
    private UUID deviceProfileId;
    private String label;
    private String name;
    private Long version;

    public Device toDevice() {
        return Device.builder()
                .id(id == null ? null : new DeviceId(id))
                .customerId(new CustomerId(customerId))
                .deviceProfileId(new DeviceProfileId(deviceProfileId))
                .label(label)
                .name(name)
                .version(version)
                .build();
    }
}
