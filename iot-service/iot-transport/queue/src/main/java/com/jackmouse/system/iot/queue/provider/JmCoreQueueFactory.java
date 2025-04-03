package com.jackmouse.system.iot.queue.provider;

import com.jackmouse.server.gen.transport.TransportProtos;
import com.jackmouse.system.iot.queue.JmQueueConsumer;
import com.jackmouse.system.iot.queue.JmQueueProducer;
import com.jackmouse.system.iot.queue.common.JmProtoQueueMsg;

/**
 * @ClassName JmCoreQueueFactory
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/2 17:02
 * @Version 1.0
 **/
public interface JmCoreQueueFactory {
    JmQueueProducer<JmProtoQueueMsg<TransportProtos.TransportApiResponseMsg>> createTransportApiResponseProducer();

    JmQueueConsumer<JmProtoQueueMsg<TransportProtos.TransportApiRequestMsg>> createTransportApiRequestConsumer();
}
