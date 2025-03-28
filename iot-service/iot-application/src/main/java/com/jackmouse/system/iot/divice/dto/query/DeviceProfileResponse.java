package com.jackmouse.system.iot.divice.dto.query;

import com.jackmouse.system.blog.domain.valueobject.OtaPackageId;
import com.jackmouse.system.blog.domain.valueobject.RuleChainId;
import com.jackmouse.system.iot.device.entity.DeviceProfile;
import com.jackmouse.system.iot.device.valueobject.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

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
public class DeviceProfileResponse {
    private final UUID id;
    private final String name;
    private final DeviceProfileType type;
    private final String image;
    private final DeviceTransportType transportType;
    private final DeviceProfileProvisionType provisionType;
    private final String description;
    private final boolean isDefault;
    private final UUID defaultRuleChainId;
    private final UUID defaultDashboardId;
    private final String defaultQueueName;
    private final String provisionDeviceKey;
    private final UUID firmwareId;
    private final UUID softwareId;
    private final UUID defaultEdgeRuleChainId;
    private final Long version;
    public static DeviceProfileResponse from(DeviceProfile deviceProfile) {

        return DeviceProfileResponse.builder()
                .id(deviceProfile.getId().getValue())
                .name(deviceProfile.getName())
                .type(deviceProfile.getType())
                .image(deviceProfile.getImage())
                .transportType(deviceProfile.getTransportType())
                .provisionType(deviceProfile.getProvisionType())
                .description(deviceProfile.getDescription())
                .isDefault(deviceProfile.isDefault())
                .defaultRuleChainId(deviceProfile.getDefaultRuleChainId().getValue())
                .defaultDashboardId(deviceProfile.getDefaultDashboardId().getValue())
                .defaultQueueName(deviceProfile.getDefaultQueueName())
                .provisionDeviceKey(deviceProfile.getProvisionDeviceKey())
                .firmwareId(deviceProfile.getFirmwareId().getValue())
                .softwareId(deviceProfile.getSoftwareId().getValue())
                .defaultEdgeRuleChainId(deviceProfile.getDefaultEdgeRuleChainId().getValue())
                .version(deviceProfile.getVersion())
                .build();
    }


}
