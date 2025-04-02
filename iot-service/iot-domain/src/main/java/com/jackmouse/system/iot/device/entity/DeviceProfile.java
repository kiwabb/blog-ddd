package com.jackmouse.system.iot.device.entity;

import com.jackmouse.system.blog.domain.entity.AggregateRoot;
import com.jackmouse.system.blog.domain.valueobject.DeviceTransportType;
import com.jackmouse.system.blog.domain.valueobject.OtaPackageId;
import com.jackmouse.system.blog.domain.valueobject.RuleChainId;
import com.jackmouse.system.iot.device.valueobject.*;

import java.io.Serializable;


/**
 * @ClassName DeviceProfile
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/28 14:02
 * @Version 1.0
 **/
public class DeviceProfile extends AggregateRoot<DeviceProfileId> implements Serializable {
    private final String name;
    private final DeviceProfileType type;
    private final String image;
    private final DeviceTransportType transportType;
    private final DeviceProfileProvisionType provisionType;
    private final String description;
    private final boolean isDefault;
    private final RuleChainId defaultRuleChainId;
    private final DefaultDashboardId defaultDashboardId;
    private final String defaultQueueName;
    private final DeviceProfileData profileData;
    private final String provisionDeviceKey;
    private final OtaPackageId firmwareId;
    private final OtaPackageId softwareId;
    private final RuleChainId defaultEdgeRuleChainId;
    private final Long version;

    public String getName() {
        return name;
    }

    public DeviceProfileType getType() {
        return type;
    }

    public String getImage() {
        return image;
    }

    public DeviceTransportType getTransportType() {
        return transportType;
    }

    public DeviceProfileProvisionType getProvisionType() {
        return provisionType;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public RuleChainId getDefaultRuleChainId() {
        return defaultRuleChainId;
    }

    public DefaultDashboardId getDefaultDashboardId() {
        return defaultDashboardId;
    }

    public String getDefaultQueueName() {
        return defaultQueueName;
    }

    public DeviceProfileData getProfileData() {
        return profileData;
    }

    public String getProvisionDeviceKey() {
        return provisionDeviceKey;
    }

    public OtaPackageId getFirmwareId() {
        return firmwareId;
    }

    public OtaPackageId getSoftwareId() {
        return softwareId;
    }

    public RuleChainId getDefaultEdgeRuleChainId() {
        return defaultEdgeRuleChainId;
    }

    public Long getVersion() {
        return version;
    }


    private DeviceProfile(Builder builder) {
        setId(builder.deviceProfileId);
        name = builder.name;
        type = builder.type;
        image = builder.image;
        transportType = builder.transportType;
        provisionType = builder.provisionType;
        description = builder.description;
        isDefault = builder.isDefault;
        defaultRuleChainId = builder.defaultRuleChainId;
        defaultDashboardId = builder.defaultDashboardId;
        defaultQueueName = builder.defaultQueueName;
        profileData = builder.profileData;
        provisionDeviceKey = builder.provisionDeviceKey;
        firmwareId = builder.firmwareId;
        softwareId = builder.softwareId;
        defaultEdgeRuleChainId = builder.defaultEdgeRuleChainId;
        version = builder.version;
    }

    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {
        private DeviceProfileId deviceProfileId;
        private String name;
        private DeviceProfileType type;
        private String image;
        private DeviceTransportType transportType;
        private DeviceProfileProvisionType provisionType;
        private String description;
        private boolean isDefault;
        private RuleChainId defaultRuleChainId;
        private DefaultDashboardId defaultDashboardId;
        private String defaultQueueName;
        private DeviceProfileData profileData;
        private String provisionDeviceKey;
        private OtaPackageId firmwareId;
        private OtaPackageId softwareId;
        private RuleChainId defaultEdgeRuleChainId;
        private Long version;

        private Builder() {
        }

        public Builder deviceProfileId(DeviceProfileId val) {
            deviceProfileId = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder type(DeviceProfileType val) {
            type = val;
            return this;
        }

        public Builder image(String val) {
            image = val;
            return this;
        }

        public Builder transportType(DeviceTransportType val) {
            transportType = val;
            return this;
        }

        public Builder provisionType(DeviceProfileProvisionType val) {
            provisionType = val;
            return this;
        }

        public Builder description(String val) {
            description = val;
            return this;
        }

        public Builder isDefault(boolean val) {
            isDefault = val;
            return this;
        }

        public Builder defaultRuleChainId(RuleChainId val) {
            defaultRuleChainId = val;
            return this;
        }

        public Builder defaultDashboardId(DefaultDashboardId val) {
            defaultDashboardId = val;
            return this;
        }

        public Builder defaultQueueName(String val) {
            defaultQueueName = val;
            return this;
        }

        public Builder profileData(DeviceProfileData val) {
            profileData = val;
            return this;
        }

        public Builder provisionDeviceKey(String val) {
            provisionDeviceKey = val;
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

        public Builder defaultEdgeRuleChainId(RuleChainId val) {
            defaultEdgeRuleChainId = val;
            return this;
        }

        public Builder version(Long val) {
            version = val;
            return this;
        }

        public DeviceProfile build() {
            return new DeviceProfile(this);
        }
    }
}
