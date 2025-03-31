package com.jackmouse.system.iot.device.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jackmouse.system.blog.domain.valueobject.OtaPackageId;
import com.jackmouse.system.converter.JsonConverter;
import com.jackmouse.system.entity.BaseEntity;
import com.jackmouse.system.entity.ToData;
import com.jackmouse.system.iot.constant.ModelConstants;
import com.jackmouse.system.iot.device.valueobject.CustomerId;
import com.jackmouse.system.iot.device.valueobject.DeviceId;
import com.jackmouse.system.iot.device.valueobject.DeviceProfileId;
import com.jackmouse.system.utils.JacksonUtil;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLJsonPGObjectJsonbType;

import java.util.UUID;

/**
 * @ClassName DeviceEntity
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/28 17:13
 * @Version 1.0
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
@NoArgsConstructor
public abstract class AbstractDeviceEntity<T> extends BaseEntity implements ToData<T> {
    @Id
    @Column(name = ModelConstants.ID_PROPERTY, columnDefinition = "uuid")
    protected UUID id;

    @Column(name = ModelConstants.DEVICE_TENANT_ID_PROPERTY, columnDefinition = "uuid")
    private UUID tenantId;

    @Column(name = ModelConstants.DEVICE_CUSTOMER_ID_PROPERTY, columnDefinition = "uuid")
    private UUID customerId;

    @Column(name = ModelConstants.DEVICE_TYPE_PROPERTY)
    private String type;

    @Column(name = ModelConstants.DEVICE_NAME_PROPERTY)
    private String name;

    @Column(name = ModelConstants.DEVICE_LABEL_PROPERTY)
    private String label;

    @Column(name = ModelConstants.DEVICE_ADDITIONAL_INFO_PROPERTY)
    private String additionalInfo;

    @Column(name = ModelConstants.DEVICE_DEVICE_PROFILE_ID_PROPERTY, columnDefinition = "uuid")
    private UUID deviceProfileId;

    @Column(name = ModelConstants.DEVICE_FIRMWARE_ID_PROPERTY, columnDefinition = "uuid")
    private UUID firmwareId;

    @Column(name = ModelConstants.DEVICE_SOFTWARE_ID_PROPERTY, columnDefinition = "uuid")
    private UUID softwareId;

    @Convert(converter = JsonConverter.class)
    @JdbcType(PostgreSQLJsonPGObjectJsonbType.class)
    @Column(name = ModelConstants.DEVICE_DEVICE_DATA_PROPERTY, columnDefinition = "jsonb")
    private JsonNode deviceData;

    @Column(name = ModelConstants.EXTERNAL_ID_PROPERTY, columnDefinition = "uuid")
    private UUID externalId;

    @Column(name = ModelConstants.VERSION_PROPERTY)
    private Long version;

    public AbstractDeviceEntity(Device device) {
        this.deviceData = JacksonUtil.convertValue(device.getDeviceData(), ObjectNode.class);
        this.name = device.getName();
        this.type = device.getType();
        this.label = device.getLabel();
        this.additionalInfo = device.getAdditionalInfo();
    }

    protected Device toDevice() {
        return Device.builder()
                .id(new DeviceId(id))
                .customerId(new CustomerId(customerId))
                .name(name)
                .type(type)
                .label(label)
                .deviceProfileId(new DeviceProfileId(deviceProfileId))
                .deviceDataBytes(deviceData.toString().getBytes())
                .firmwareId(new OtaPackageId(firmwareId))
                .softwareId(new OtaPackageId(softwareId))
                .externalId(new DeviceId(externalId))
                .version(version)
                .build();
    }



}
