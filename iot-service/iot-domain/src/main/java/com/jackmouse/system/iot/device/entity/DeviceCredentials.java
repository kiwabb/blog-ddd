package com.jackmouse.system.iot.device.entity;

import com.jackmouse.system.blog.domain.entity.AggregateRoot;
import com.jackmouse.system.blog.domain.valueobject.BaseId;
import com.jackmouse.system.iot.device.valueobject.DeviceCredentialsId;
import com.jackmouse.system.iot.device.valueobject.DeviceCredentialsType;
import com.jackmouse.system.iot.device.valueobject.DeviceId;

/**
 * @ClassName DeviceCredentials
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/3 09:31
 * @Version 1.0
 **/
public class DeviceCredentials extends AggregateRoot<DeviceCredentialsId> {

    private DeviceId deviceId;
    private DeviceCredentialsType credentialsType;
    private String credentialsId;
    private String credentialsValue;
    private Long version;

    public DeviceId getDeviceId() {
        return deviceId;
    }

    public DeviceCredentialsType getCredentialsType() {
        return credentialsType;
    }

    public String getCredentialsId() {
        return credentialsId;
    }

    public String getCredentialsValue() {
        return credentialsValue;
    }

    public Long getVersion() {
        return version;
    }

    private DeviceCredentials(Builder builder) {
        setId(builder.id);
        deviceId = builder.deviceId;
        credentialsType = builder.credentialsType;
        credentialsId = builder.credentialsId;
        credentialsValue = builder.credentialsValue;
        version = builder.version;
    }

    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {
        private DeviceCredentialsId id;
        private DeviceId deviceId;
        private DeviceCredentialsType credentialsType;
        private String credentialsId;
        private String credentialsValue;
        private Long version;

        private Builder() {
        }

        public Builder id(DeviceCredentialsId val) {
            id = val;
            return this;
        }

        public Builder deviceId(DeviceId val) {
            deviceId = val;
            return this;
        }

        public Builder credentialsType(DeviceCredentialsType val) {
            credentialsType = val;
            return this;
        }

        public Builder credentialsId(String val) {
            credentialsId = val;
            return this;
        }

        public Builder credentialsValue(String val) {
            credentialsValue = val;
            return this;
        }

        public Builder version(Long val) {
            version = val;
            return this;
        }

        public DeviceCredentials build() {
            return new DeviceCredentials(this);
        }
    }
}
