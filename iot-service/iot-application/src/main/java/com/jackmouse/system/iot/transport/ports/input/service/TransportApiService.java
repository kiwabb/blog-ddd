package com.jackmouse.system.iot.transport.ports.input.service;

import com.jackmouse.server.gen.transport.TransportProtos;
import com.jackmouse.system.iot.queue.JmQueueHandler;
import com.jackmouse.system.iot.queue.common.JmProtoQueueMsg;

/**
 * @ClassName TransportApiService
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/3 09:11
 * @Version 1.0
 **/
public interface TransportApiService extends JmQueueHandler<
        JmProtoQueueMsg<TransportProtos.TransportApiRequestMsg>,
        JmProtoQueueMsg<TransportProtos.TransportApiResponseMsg>> {
}
