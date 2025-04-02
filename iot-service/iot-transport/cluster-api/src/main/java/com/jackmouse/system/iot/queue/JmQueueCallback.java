package com.jackmouse.system.iot.queue;

/**
 * @ClassName JmQueueCallback
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/1 16:46
 * @Version 1.0
 **/
public interface JmQueueCallback {
    JmQueueCallback EMPTY = new JmQueueCallback() {

        @Override
        public void onSuccess(JmQueueMsgMetadata metadata) {

        }

        @Override
        public void onFailure(Throwable t) {

        }
    };

    void onSuccess(JmQueueMsgMetadata metadata);

    void onFailure(Throwable t);
}
