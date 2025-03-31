package com.jackmouse.system.iot.device.valueobject;

import com.jackmouse.system.iot.device.specification.DeviceConfiguration;
import com.jackmouse.system.iot.device.specification.DeviceTransportConfiguration;

/**
 * @ClassName DeviceData
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/31 15:17
 * @Version 1.0
 **/
public class DeviceData {
    private DeviceConfiguration configuration;
    private DeviceTransportConfiguration transportConfiguration;

    public DeviceData() {
    }

    public void setConfiguration(DeviceConfiguration configuration) {
        this.configuration = configuration;
    }

    public void setTransportConfiguration(DeviceTransportConfiguration transportConfiguration) {
        this.transportConfiguration = transportConfiguration;
    }

    public DeviceConfiguration getConfiguration() {
        return configuration;
    }

    public DeviceTransportConfiguration getTransportConfiguration() {
        return transportConfiguration;
    }

    private DeviceData(Builder builder) {
        configuration = builder.configuration;
        transportConfiguration = builder.transportConfiguration;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private DeviceConfiguration configuration;
        private DeviceTransportConfiguration transportConfiguration;

        private Builder() {
        }

        public Builder configuration(DeviceConfiguration val) {
            configuration = val;
            return this;
        }

        public Builder transportConfiguration(DeviceTransportConfiguration val) {
            transportConfiguration = val;
            return this;
        }

        public DeviceData build() {
            return new DeviceData(this);
        }
    }
}
