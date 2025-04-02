package com.jackmouse.system.iot.queue;


import com.google.common.util.concurrent.ListenableFuture;

/**
 * @ClassName JmQueueRequestTemplate
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/1 20:10
 * @Version 1.0
 **/
public interface JmQueueRequestTemplate<Request extends JmQueueMsg, Response extends JmQueueMsg> {
    void init();
    ListenableFuture<Response> send(Request request);
    ListenableFuture<Response> send(Request request, long timeoutNs);
    ListenableFuture<Response> send(Request request, Integer partition);
    void stop();

}
