package com.jackmouse.system.iot.device.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.jackmouse.system.converter.JsonConverter;
import com.jackmouse.system.entity.BaseEntity;
import com.jackmouse.system.iot.device.constant.ModelConstants;
import com.jackmouse.system.iot.device.valueobject.DeviceProfileProvisionType;
import com.jackmouse.system.iot.device.valueobject.DeviceProfileType;
import com.jackmouse.system.iot.device.valueobject.DeviceTransportType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLJsonPGObjectJsonbType;

import java.util.UUID;

/**
 * @ClassName DeviceProfileEntity
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/28 11:37
 * @Version 1.0
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = ModelConstants.DEVICE_PROFILE_TABLE_NAME)
@ToString(callSuper = true)
public class DeviceProfileEntity extends BaseEntity {

    @Id
    @Column(name = ModelConstants.ID_PROPERTY, columnDefinition = "uuid")
    protected UUID id;

    @Column(name = ModelConstants.DEVICE_PROFILE_TENANT_ID_PROPERTY)
    private Long tenantId;

    @Column(name = ModelConstants.DEVICE_PROFILE_NAME_PROPERTY)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = ModelConstants.DEVICE_PROFILE_TYPE_PROPERTY)
    private DeviceProfileType type;

    @Column(name = ModelConstants.DEVICE_PROFILE_IMAGE_PROPERTY)
    private String image;

    @Enumerated(EnumType.STRING)
    @Column(name = ModelConstants.DEVICE_PROFILE_TRANSPORT_TYPE_PROPERTY)
    private DeviceTransportType transportType;

    @Enumerated(EnumType.STRING)
    @Column(name = ModelConstants.DEVICE_PROFILE_PROVISION_TYPE_PROPERTY)
    private DeviceProfileProvisionType provisionType;

    @Column(name = ModelConstants.DEVICE_PROFILE_DESCRIPTION_PROPERTY)
    private String description;

    @Column(name = ModelConstants.DEVICE_PROFILE_IS_DEFAULT_PROPERTY)
    private boolean isDefault;

    @Column(name = ModelConstants.DEVICE_PROFILE_DEFAULT_RULE_CHAIN_ID_PROPERTY, columnDefinition = "uuid")
    private UUID defaultRuleChainId;

    @Column(name = ModelConstants.DEVICE_PROFILE_DEFAULT_DASHBOARD_ID_PROPERTY)
    private UUID defaultDashboardId;

    @Column(name = ModelConstants.DEVICE_PROFILE_DEFAULT_QUEUE_NAME_PROPERTY)
    private String defaultQueueName;

    @Convert(converter = JsonConverter.class)
    @JdbcType(PostgreSQLJsonPGObjectJsonbType.class)
    @Column(name = ModelConstants.DEVICE_PROFILE_PROFILE_DATA_PROPERTY, columnDefinition = "jsonb")
    private JsonNode profileData;

    @Column(name = ModelConstants.DEVICE_PROFILE_PROVISION_DEVICE_KEY)
    private String provisionDeviceKey;

    @Column(name = ModelConstants.DEVICE_PROFILE_FIRMWARE_ID_PROPERTY)
    private UUID firmwareId;

    @Column(name = ModelConstants.DEVICE_PROFILE_SOFTWARE_ID_PROPERTY)
    private UUID softwareId;

    @Column(name = ModelConstants.DEVICE_PROFILE_DEFAULT_EDGE_RULE_CHAIN_ID_PROPERTY, columnDefinition = "uuid")
    private UUID defaultEdgeRuleChainId;

    @Column(name = ModelConstants.EXTERNAL_ID_PROPERTY)
    private UUID externalId;
}
