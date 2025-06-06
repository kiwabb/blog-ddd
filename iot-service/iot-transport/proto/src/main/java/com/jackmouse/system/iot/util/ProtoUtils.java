package com.jackmouse.system.iot.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.jackmouse.server.gen.transport.TransportProtos;
import com.jackmouse.system.blog.domain.valueobject.BaseId;
import com.jackmouse.system.blog.domain.valueobject.DeviceTransportType;
import com.jackmouse.system.blog.domain.valueobject.OtaPackageId;
import com.jackmouse.system.blog.domain.valueobject.RuleChainId;
import com.jackmouse.system.iot.device.entity.Device;
import com.jackmouse.system.iot.device.entity.DeviceProfile;
import com.jackmouse.system.iot.device.valueobject.DefaultDashboardId;
import com.jackmouse.system.iot.device.valueobject.DeviceProfileId;
import com.jackmouse.system.iot.device.valueobject.DeviceProfileProvisionType;
import com.jackmouse.system.iot.device.valueobject.DeviceProfileType;
import com.jackmouse.system.utils.JacksonUtil;

import java.util.UUID;
import java.util.function.Function;

import static com.jackmouse.system.utils.ObjectUtil.isNotNull;

/**
 * @ClassName ProteUtils
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/2 14:17
 * @Version 1.0
 **/
public class ProtoUtils {
    public static DeviceProfile fromProto(TransportProtos.DeviceProfileProto proto) {
        DeviceProfile.Builder builder = DeviceProfile.builder();
        builder.deviceProfileId(getEntityId(proto.getDeviceProfileIdMSB(), proto.getDeviceProfileIdLSB(), DeviceProfileId::new));
        builder.name(proto.getName());
        builder.isDefault(proto.getIsDefault());
        builder.type(DeviceProfileType.valueOf(proto.getType()));
        builder.transportType(DeviceTransportType.valueOf(proto.getTransportType()));
        builder.provisionType(DeviceProfileProvisionType.valueOf(proto.getProvisionType()));
        if (proto.hasDescription()) {
            builder.description(proto.getDescription());
        }
        if (proto.hasImage()) {
            builder.image(proto.getImage());
        }
        if (proto.hasDefaultRuleChainIdMSB() && proto.hasDefaultRuleChainIdLSB()) {
            builder.defaultRuleChainId(getEntityId(proto.getDefaultRuleChainIdMSB(), proto.getDefaultRuleChainIdLSB(), RuleChainId::new));
        }
        if (proto.hasDefaultDashboardIdMSB() && proto.hasDefaultDashboardIdLSB()) {
            builder.defaultDashboardId(getEntityId(proto.getDefaultDashboardIdMSB(), proto.getDefaultDashboardIdLSB(), DefaultDashboardId::new));
        }
        if (proto.hasDefaultQueueName()) {
            builder.defaultQueueName(proto.getDefaultQueueName());
        }
        if (proto.hasProvisionDeviceKey()) {
            builder.provisionDeviceKey(proto.getProvisionDeviceKey());
        }
        if (proto.hasFirmwareIdMSB() && proto.hasFirmwareIdLSB()) {
            builder.firmwareId(getEntityId(proto.getFirmwareIdMSB(), proto.getFirmwareIdLSB(), OtaPackageId::new));
        }
        if (proto.hasSoftwareIdMSB() && proto.hasSoftwareIdLSB()) {
            builder.softwareId(getEntityId(proto.getSoftwareIdMSB(), proto.getSoftwareIdLSB(), OtaPackageId::new));
        }

        if (proto.hasDefaultEdgeRuleChainIdMSB() && proto.hasDefaultEdgeRuleChainIdLSB()) {
            builder.defaultEdgeRuleChainId(getEntityId(proto.getDefaultEdgeRuleChainIdMSB(), proto.getDefaultEdgeRuleChainIdLSB(), RuleChainId::new));
        }
        if (proto.hasVersion()) {
            builder.version(proto.getVersion());
        }
        return builder.build();
    }
    private static <T extends BaseId<UUID>> T getEntityId(long msb, long lsb, Function<UUID, T> entityId) {
        return entityId.apply(new UUID(msb, lsb));
    }

    public static TransportProtos.DeviceInfoProto toDeviceInfoProto(Device device) throws JsonProcessingException {
        TransportProtos.DeviceInfoProto.Builder builder = TransportProtos.DeviceInfoProto.newBuilder()
                .setCustomerIdLSB(getLsb(device.getCustomerId()))
                .setCustomerIdMSB(getMsb(device.getCustomerId()))
                .setDeviceIdLSB(getLsb(device.getId()))
                .setDeviceIdMSB(getMsb(device.getId()))
                .setDeviceProfileIdLSB(getLsb(device.getDeviceProfileId()))
                .setDeviceProfileIdMSB(getMsb(device.getDeviceProfileId()))
                .setDeviceName(device.getName())
                .setDeviceType(device.getType())
                .setAdditionalInfo(device.getAdditionalInfo());
        final JsonNode jsonNode = JacksonUtil.toJsonNode(device.getAdditionalInfo());
        if (jsonNode.has("gateway")) {
            builder.setIsGateway(jsonNode.get("gateway").booleanValue());
        }
        return builder.build();
    }

    public static TransportProtos.DeviceProfileProto toProto(DeviceProfile deviceProfile) {
        var builder = TransportProtos.DeviceProfileProto.newBuilder()
                .setDeviceProfileIdMSB(getMsb(deviceProfile.getId()))
                .setDeviceProfileIdLSB(getLsb(deviceProfile.getId()))
                .setName(deviceProfile.getName())
                .setIsDefault(deviceProfile.isDefault())
                .setType(deviceProfile.getType().name())
                .setTransportType(deviceProfile.getTransportType().name())
                .setProvisionType(deviceProfile.getProvisionType().name());
        return builder.build();
    }
    private static Long getMsb(BaseId<UUID> entityId) {
        if (isNotNull(entityId)) {
            return entityId.getValue().getMostSignificantBits();
        }
        return 0L;
    }

    private static Long getLsb(BaseId<UUID> entityId) {
        if (isNotNull(entityId)) {
            return entityId.getValue().getLeastSignificantBits();
        }
        return 0L;
    }
}
