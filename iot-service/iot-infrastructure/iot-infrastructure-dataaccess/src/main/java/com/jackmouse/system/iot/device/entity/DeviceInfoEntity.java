package com.jackmouse.system.iot.device.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.jackmouse.system.blog.domain.valueobject.OtaPackageId;
import com.jackmouse.system.converter.JsonConverter;
import com.jackmouse.system.entity.BaseEntity;
import com.jackmouse.system.entity.ToData;
import com.jackmouse.system.iot.constant.ModelConstants;
import com.jackmouse.system.iot.device.valueobject.CustomerId;
import com.jackmouse.system.iot.device.valueobject.DeviceId;
import com.jackmouse.system.iot.device.valueobject.DeviceInfo;
import com.jackmouse.system.iot.device.valueobject.DeviceProfileId;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@Entity
@Table(name = ModelConstants.DEVICE_INFO_VIEW_TABLE_NAME)
public class DeviceInfoEntity extends AbstractDeviceEntity<DeviceInfo>{
    @Column(name = ModelConstants.DEVICE_CUSTOMER_TITLE_PROPERTY)
    private String customerTitle;
    @Column(name = ModelConstants.DEVICE_CUSTOMER_IS_PUBLIC_PROPERTY)
    private boolean customerIsPublic;
    @Column(name = ModelConstants.DEVICE_DEVICE_PROFILE_NAME_PROPERTY)
    private String deviceProfileName;
    @Column(name = ModelConstants.DEVICE_ACTIVE_PROPERTY)
    private boolean active;

    @Override
    public DeviceInfo toData() {
        return DeviceInfo.builder()
                .device(super.toDevice())
                .customerTitle(customerTitle)
                .customerIsPublic(customerIsPublic)
                .deviceProfileName(deviceProfileName)
                .customerTitle(customerTitle)
                .build();
    }
}
