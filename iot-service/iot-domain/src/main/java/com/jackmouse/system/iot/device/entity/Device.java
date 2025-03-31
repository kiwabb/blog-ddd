package com.jackmouse.system.iot.device.entity;

import com.jackmouse.system.blog.domain.entity.AggregateRoot;
import com.jackmouse.system.blog.domain.valueobject.OtaPackageId;
import com.jackmouse.system.iot.device.valueobject.CustomerId;
import com.jackmouse.system.iot.device.valueobject.DeviceId;
import com.jackmouse.system.iot.device.valueobject.DeviceProfileId;

import java.time.ZonedDateTime;

/**
 * @ClassName Device
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/28 17:15
 * @Version 1.0
 **/
public class Device extends AggregateRoot<DeviceId> {
    private final CustomerId customerId;
    private final String name;
    private final String type;
    private final String label;
    private final DeviceProfileId deviceProfileId;
    private final byte[] deviceDataBytes;
    private final OtaPackageId firmwareId;
    private final OtaPackageId softwareId;
    private final DeviceId externalId;
    private final Long version;
    private final ZonedDateTime createdAt;

    public CustomerId getCustomerId() {
        return customerId;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getLabel() {
        return label;
    }

    public DeviceProfileId getDeviceProfileId() {
        return deviceProfileId;
    }

    public byte[] getDeviceDataBytes() {
        return deviceDataBytes;
    }

    public OtaPackageId getFirmwareId() {
        return firmwareId;
    }

    public OtaPackageId getSoftwareId() {
        return softwareId;
    }

    public DeviceId getExternalId() {
        return externalId;
    }

    public Long getVersion() {
        return version;
    }

    private Device(Builder builder) {
        setId(builder.id);
        customerId = builder.customerId;
        name = builder.name;
        type = builder.type;
        label = builder.label;
        deviceProfileId = builder.deviceProfileId;
        deviceDataBytes = builder.deviceDataBytes;
        firmwareId = builder.firmwareId;
        softwareId = builder.softwareId;
        externalId = builder.externalId;
        version = builder.version;
        createdAt = builder.createdAt;
    }

    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {
        private DeviceId id;
        private CustomerId customerId;
        private String name;
        private String type;
        private String label;
        private DeviceProfileId deviceProfileId;
        private byte[] deviceDataBytes;
        private OtaPackageId firmwareId;
        private OtaPackageId softwareId;
        private DeviceId externalId;
        private Long version;
        private ZonedDateTime createdAt;

        private Builder() {
        }

        public Builder id(DeviceId val) {
            id = val;
            return this;
        }

        public Builder customerId(CustomerId val) {
            customerId = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder type(String val) {
            type = val;
            return this;
        }

        public Builder label(String val) {
            label = val;
            return this;
        }

        public Builder deviceProfileId(DeviceProfileId val) {
            deviceProfileId = val;
            return this;
        }

        public Builder deviceDataBytes(byte[] val) {
            deviceDataBytes = val;
            return this;
        }

        public Builder firmwareId(OtaPackageId val) {
            firmwareId = val;
            return this;
        }

        public Builder softwareId(OtaPackageId val) {
            softwareId = val;
            return this;
        }

        public Builder externalId(DeviceId val) {
            externalId = val;
            return this;
        }

        public Builder version(Long val) {
            version = val;
            return this;
        }

        public Builder createdAt(ZonedDateTime val) {
            createdAt = val;
            return this;
        }

        public Device build() {
            return new Device(this);
        }
    }
}
