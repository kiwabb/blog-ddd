package com.jackmouse.system.iot.device.valueobject;

import com.jackmouse.system.iot.device.entity.Device;

/**
 * @ClassName DeviceInfo
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/31 13:00
 * @Version 1.0
 **/
public class DeviceInfo {
    private Device device;
    private String customerTitle;
    private boolean customerIsPublic;
    private String deviceProfileName;
    private boolean active;

    public Device getDevice() {
        return device;
    }

    public String getCustomerTitle() {
        return customerTitle;
    }

    public boolean isCustomerIsPublic() {
        return customerIsPublic;
    }

    public String getDeviceProfileName() {
        return deviceProfileName;
    }

    public boolean isActive() {
        return active;
    }

    private DeviceInfo(Builder builder) {
        device = builder.device;
        customerTitle = builder.customerTitle;
        customerIsPublic = builder.customerIsPublic;
        deviceProfileName = builder.deviceProfileName;
        active = builder.active;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Device device;
        private String customerTitle;
        private boolean customerIsPublic;
        private String deviceProfileName;
        private boolean active;

        private Builder() {
        }

        public Builder device(Device val) {
            device = val;
            return this;
        }

        public Builder customerTitle(String val) {
            customerTitle = val;
            return this;
        }

        public Builder customerIsPublic(boolean val) {
            customerIsPublic = val;
            return this;
        }

        public Builder deviceProfileName(String val) {
            deviceProfileName = val;
            return this;
        }

        public Builder active(boolean val) {
            active = val;
            return this;
        }

        public DeviceInfo build() {
            return new DeviceInfo(this);
        }
    }
}
