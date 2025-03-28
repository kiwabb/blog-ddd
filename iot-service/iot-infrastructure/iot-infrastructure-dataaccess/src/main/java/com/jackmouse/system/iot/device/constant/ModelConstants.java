package com.jackmouse.system.iot.device.constant;

/**
 * @ClassName ModelConstans
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/28 13:04
 * @Version 1.0
 **/
public class ModelConstants {
    private ModelConstants() {
    }

    /**
     * Generic constants.
     */
    public static final String ID_PROPERTY = "id";
    public static final String TENANT_ID_PROPERTY = "tenant_id";


    /**
     * Edge queue constants.
     */
    public static final String EXTERNAL_ID_PROPERTY = "external_id";


    /**
     * Device profile constants.
     */
    public static final String DEVICE_PROFILE_TABLE_NAME = "device_profile";
    public static final String DEVICE_PROFILE_TENANT_ID_PROPERTY = TENANT_ID_PROPERTY;
    public static final String DEVICE_PROFILE_NAME_PROPERTY = "name";
    public static final String DEVICE_PROFILE_TYPE_PROPERTY = "type";
    public static final String DEVICE_PROFILE_IMAGE_PROPERTY = "image";
    public static final String DEVICE_PROFILE_TRANSPORT_TYPE_PROPERTY = "transport_type";
    public static final String DEVICE_PROFILE_PROVISION_TYPE_PROPERTY = "provision_type";
    public static final String DEVICE_PROFILE_PROFILE_DATA_PROPERTY = "profile_data";
    public static final String DEVICE_PROFILE_DESCRIPTION_PROPERTY = "description";
    public static final String DEVICE_PROFILE_IS_DEFAULT_PROPERTY = "is_default";
    public static final String DEVICE_PROFILE_DEFAULT_RULE_CHAIN_ID_PROPERTY = "default_rule_chain_id";
    public static final String DEVICE_PROFILE_DEFAULT_DASHBOARD_ID_PROPERTY = "default_dashboard_id";
    public static final String DEVICE_PROFILE_DEFAULT_QUEUE_NAME_PROPERTY = "default_queue_name";
    public static final String DEVICE_PROFILE_PROVISION_DEVICE_KEY = "provision_device_key";
    public static final String DEVICE_PROFILE_FIRMWARE_ID_PROPERTY = "firmware_id";
    public static final String DEVICE_PROFILE_SOFTWARE_ID_PROPERTY = "software_id";
    public static final String DEVICE_PROFILE_DEFAULT_EDGE_RULE_CHAIN_ID_PROPERTY = "default_edge_rule_chain_id";

}
