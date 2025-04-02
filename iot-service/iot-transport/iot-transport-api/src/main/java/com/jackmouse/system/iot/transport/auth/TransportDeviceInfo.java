package com.jackmouse.system.iot.transport.auth;

import com.jackmouse.system.iot.device.valueobject.CustomerId;
import com.jackmouse.system.iot.device.valueobject.DeviceId;
import com.jackmouse.system.iot.device.valueobject.DeviceProfileId;
import com.jackmouse.system.iot.device.valueobject.PowerMode;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName TransportDeviceInfo
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/1 20:30
 * @Version 1.0
 **/
@Data
public class TransportDeviceInfo implements Serializable {
    private Object tenantId;
    private CustomerId customerId;
    private DeviceProfileId deviceProfileId;
    private DeviceId deviceId;
    private String deviceName;
    private String deviceType;
    private PowerMode powerMode;
    private String additionalInfo;
    private Long edrxCycle;
    private Long psmActivityTimer;
    private Long pagingTransmissionWindow;
    private boolean gateway;
}
