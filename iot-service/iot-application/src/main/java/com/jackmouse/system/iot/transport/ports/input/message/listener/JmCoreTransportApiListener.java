package com.jackmouse.system.iot.transport.ports.input.message.listener;

import com.jackmouse.server.gen.transport.TransportProtos;
import com.jackmouse.system.iot.queue.JmQueueConsumer;
import com.jackmouse.system.iot.queue.JmQueueMsg;
import com.jackmouse.system.iot.queue.JmQueueProducer;
import com.jackmouse.system.iot.queue.JmQueueResponseTemplate;
import com.jackmouse.system.iot.queue.common.DefaultJmQueueRequestTemplate;
import com.jackmouse.system.iot.queue.common.DefaultJmQueueResponseTemplate;
import com.jackmouse.system.iot.queue.common.JmProtoQueueMsg;
import com.jackmouse.system.iot.queue.provider.JmCoreQueueFactory;
import com.jackmouse.system.iot.transport.ports.input.service.TransportApiService;
import com.jackmouse.system.utils.JackmouseExecutor;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;

/**
 * @ClassName JmCoreTransportApiListener
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/2 16:29
 * @Version 1.0
 **/
@Slf4j
@Component
public class JmCoreTransportApiListener {


    private final TransportApiService transportApiService;

    private final JmCoreQueueFactory jmCoreQueueFactory;
    @Value("${queue.transport_api.max_pending_requests:10000}")
    private int maxPendingRequests;
    @Value("${queue.transport_api.max_requests_timeout:10000}")
    private long requestTimeout;
    @Value("${queue.transport_api.request_poll_interval:25}")
    private int responsePollDuration;
    @Value("${queue.transport_api.max_callback_threads:100}")
    private int maxCallbackThreads;

    private ExecutorService transportCallbackExecutor;
    private JmQueueResponseTemplate<
            JmProtoQueueMsg<TransportProtos.TransportApiRequestMsg>,
            JmProtoQueueMsg<TransportProtos.TransportApiResponseMsg>> transportApiTemplate;

    public JmCoreTransportApiListener(TransportApiService transportApiService, JmCoreQueueFactory jmCoreQueueFactory) {
        this.transportApiService = transportApiService;
        this.jmCoreQueueFactory = jmCoreQueueFactory;
    }

    @PostConstruct
    public void init() {
        this.transportCallbackExecutor = JackmouseExecutor.newWorkStealingPool(maxCallbackThreads, getClass());
        JmQueueProducer<JmProtoQueueMsg<TransportProtos.TransportApiResponseMsg>> producer = jmCoreQueueFactory.createTransportApiResponseProducer();
        JmQueueConsumer<JmProtoQueueMsg<TransportProtos.TransportApiRequestMsg>> consumer = jmCoreQueueFactory.createTransportApiRequestConsumer();
        DefaultJmQueueResponseTemplate.DefaultJmQueueResponseTemplateBuilder<
                JmProtoQueueMsg<TransportProtos.TransportApiRequestMsg>,
                JmProtoQueueMsg<TransportProtos.TransportApiResponseMsg>> builder = DefaultJmQueueResponseTemplate.builder();
        builder.requestTemplate(consumer);
        builder.responseTemplate(producer);
        builder.maxPendingRequests(maxPendingRequests);
        builder.requestTimeout(requestTimeout);
        builder.pollInterval(responsePollDuration);
        builder.executor(transportCallbackExecutor);
        transportApiTemplate = builder.build();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        log.info("接收到应用就绪事件，开始轮训事件");
        transportApiTemplate.subscribe();
        transportApiTemplate.launch(transportApiService);
    }
}
