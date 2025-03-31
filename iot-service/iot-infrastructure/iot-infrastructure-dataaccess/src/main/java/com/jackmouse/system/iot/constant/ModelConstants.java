package com.jackmouse.system.iot.constant;

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
    public static final String VERSION_PROPERTY = "version";
    public static final String CUSTOMER_ID_PROPERTY = "customer_id";
    public static final String ADDITIONAL_INFO_PROPERTY = "additional_info";




    /**
     * Edge queue constants.
     */
    public static final String EXTERNAL_ID_PROPERTY = "external_id";


    /**
     * Device constants.
     */
    public static final String DEVICE_TABLE_NAME = "device";
    public static final String DEVICE_TENANT_ID_PROPERTY = TENANT_ID_PROPERTY;
    public static final String DEVICE_CUSTOMER_ID_PROPERTY = CUSTOMER_ID_PROPERTY;
    public static final String DEVICE_NAME_PROPERTY = "name";
    public static final String DEVICE_TYPE_PROPERTY = "type";
    public static final String DEVICE_LABEL_PROPERTY = "label";
    public static final String DEVICE_ADDITIONAL_INFO_PROPERTY = ADDITIONAL_INFO_PROPERTY;
    public static final String DEVICE_DEVICE_PROFILE_ID_PROPERTY = "device_profile_id";
    public static final String DEVICE_DEVICE_DATA_PROPERTY = "device_data";
    public static final String DEVICE_FIRMWARE_ID_PROPERTY = "firmware_id";
    public static final String DEVICE_SOFTWARE_ID_PROPERTY = "software_id";

    public static final String DEVICE_CUSTOMER_TITLE_PROPERTY = "customer_title";
    public static final String DEVICE_CUSTOMER_IS_PUBLIC_PROPERTY = "customer_is_public";
    public static final String DEVICE_DEVICE_PROFILE_NAME_PROPERTY = "device_profile_name";
    public static final String DEVICE_ACTIVE_PROPERTY = "active";

    public static final String DEVICE_INFO_VIEW_TABLE_NAME = "device_info_view";

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
