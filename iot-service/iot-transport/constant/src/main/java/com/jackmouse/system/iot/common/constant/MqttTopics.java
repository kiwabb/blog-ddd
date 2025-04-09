package com.jackmouse.system.iot.common.constant;

/**
 * @ClassName MqttTopics
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/8 10:56
 * @Version 1.0
 **/
public class MqttTopics {
    private static final String RPC = "/rpc";
    private static final String REQUEST = "/request";
    private static final String RESPONSE = "/response";
    private static final String TELEMETRY = "/telemetry";
    public static final String BASE_DEVICE_API_TOPIC = "v1/devices/me";
    public static final String BASE_GATEWAY_API_TOPIC = "v1/gateway";
    private static final String ATTRIBUTES = "/attributes";

    private static final String DEVICE_RPC_REQUEST = RPC + REQUEST + "/";
    private static final String ATTRIBUTES_RESPONSE = ATTRIBUTES + RESPONSE;
    private static final String DEVICE_ATTRIBUTES_RESPONSE = ATTRIBUTES_RESPONSE + "/";
    private static final String DEVICE_RPC_RESPONSE = RPC + RESPONSE + "/";
    public static final String DEVICE_TELEMETRY_TOPIC = BASE_DEVICE_API_TOPIC + TELEMETRY;
    public static final String DEVICE_ATTRIBUTES_RESPONSE_TOPIC_PREFIX = BASE_DEVICE_API_TOPIC + DEVICE_ATTRIBUTES_RESPONSE;
    public static final String DEVICE_ATTRIBUTES_TOPIC = BASE_DEVICE_API_TOPIC + ATTRIBUTES;
    public static final String DEVICE_RPC_REQUESTS_TOPIC = BASE_DEVICE_API_TOPIC + DEVICE_RPC_REQUEST;
    public static final String DEVICE_RPC_RESPONSE_TOPIC = BASE_DEVICE_API_TOPIC + DEVICE_RPC_RESPONSE;





}
