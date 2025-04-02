package com.jackmouse.system.iot.transport;

/**
 * @ClassName TransportServiceCallback
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/1 13:37
 * @Version 1.0
 **/
public interface TransportServiceCallback<T> {
    TransportServiceCallback<Void> EMPTY = new TransportServiceCallback<Void>() {
        @Override
        public void onSuccess(Void msg) {

        }

        @Override
        public void onFailure(Throwable t) {

        }
    };

    void onSuccess(T msg);

    void onFailure(Throwable t);
}
