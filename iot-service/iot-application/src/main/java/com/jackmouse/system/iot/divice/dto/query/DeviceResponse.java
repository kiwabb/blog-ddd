package com.jackmouse.system.iot.divice.dto.query;

import com.fasterxml.jackson.databind.JsonNode;
import com.jackmouse.system.iot.device.entity.Device;
import com.jackmouse.system.utils.JacksonUtil;
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
    private final JsonNode additionalInfo;
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
                .additionalInfo(JacksonUtil.toJsonNode(device.getAdditionalInfo()))
                .createdAt(device.getCreatedAt())
                .build();
    }


}
