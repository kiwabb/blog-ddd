package com.jackmouse.system.iot.queue;

import com.google.common.util.concurrent.ListenableFuture;

/**
 * @ClassName JmQueueHandler
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/2 16:41
 * @Version 1.0
 **/
public interface JmQueueHandler<Request extends JmQueueMsg, Response extends JmQueueMsg> {
    ListenableFuture<Response> handle(Request request);
}
