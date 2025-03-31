package com.jackmouse.system.iot.device.entity;

import com.jackmouse.system.iot.constant.ModelConstants;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@Builder
@Table(name = ModelConstants.DEVICE_TABLE_NAME)
public class DeviceEntity extends AbstractDeviceEntity<Device> {
    public DeviceEntity() {
        super();
    }

    public DeviceEntity(Device device) {
        super(device);
    }

    @Override
    public Device toData() {
        return super.toDevice();
    }
}
