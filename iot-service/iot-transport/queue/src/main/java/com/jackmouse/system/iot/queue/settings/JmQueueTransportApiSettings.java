package com.jackmouse.system.iot.queue.settings;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @ClassName JmQueueTransportApiSettings
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/2 10:53
 * @Version 1.0
 **/
@Lazy
@Data
@Component
public class JmQueueTransportApiSettings {
    @Value("${queue.transport_api.requests_topic}")
    private String requestsTopic;

    @Value("${queue.transport_api.responses_topic}")
    private String responsesTopic;

    @Value("${queue.transport_api.max_pending_requests}")
    private int maxPendingRequests;

    @Value("${queue.transport_api.max_requests_timeout}")
    private int maxRequestsTimeout;

    @Value("${queue.transport_api.max_callback_threads}")
    private int maxCallbackThreads;

    @Value("${queue.transport_api.request_poll_interval}")
    private long requestPollInterval;

    @Value("${queue.transport_api.response_poll_interval}")
    private long responsePollInterval;
}
