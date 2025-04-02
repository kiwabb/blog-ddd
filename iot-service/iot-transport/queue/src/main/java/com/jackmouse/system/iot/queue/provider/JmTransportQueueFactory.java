package com.jackmouse.system.iot.queue.provider;

import com.jackmouse.server.gen.transport.TransportProtos;
import com.jackmouse.system.iot.queue.JmQueueRequestTemplate;
import com.jackmouse.system.iot.queue.common.JmProtoQueueMsg;

/**
 * @ClassName JmTransportQueueFactory
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/1 21:17
 * @Version 1.0
 **/
public interface JmTransportQueueFactory {
    JmQueueRequestTemplate<
            JmProtoQueueMsg<TransportProtos.TransportApiRequestMsg>,
            JmProtoQueueMsg<TransportProtos.TransportApiResponseMsg>> createTransportApiRequestTemplate();
}
