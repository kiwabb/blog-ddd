package com.jackmouse.system.iot.transport.session;

import com.jackmouse.server.gen.transport.TransportProtos;
import com.jackmouse.system.iot.device.entity.DeviceProfile;
import com.jackmouse.system.iot.device.valueobject.DeviceId;
import com.jackmouse.system.iot.transport.auth.TransportDeviceInfo;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * @ClassName DeviceAwareSessionContext
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/7 15:57
 * @Version 1.0
 **/
@Data
public abstract class DeviceAwareSessionContext implements SessionContext{
    protected final UUID sessionId;
    @Getter
    private volatile DeviceId deviceId;
    @Getter
    private volatile Object tenantId;
    @Getter
    protected volatile TransportDeviceInfo deviceInfo;
    @Getter
    @Setter
    protected volatile DeviceProfile deviceProfile;
    @Getter
    @Setter
    protected volatile TransportProtos.SessionInfoProto sessionInfo;

    @Setter
    private volatile boolean connected;

    public void setDeviceInfo(TransportDeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
        this.deviceId = deviceInfo.getDeviceId();
        this.tenantId = deviceInfo.getTenantId();
    }

    public boolean isSparkplug() {
        return false;
    }
}
