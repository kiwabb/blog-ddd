package com.jackmouse.system.iot.queue;

/**
 * @ClassName TbQueueAdmin
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/2 11:04
 * @Version 1.0
 **/
public interface JmQueueAdmin {
    default void createTopicIfNotExists(String topic) {
        createTopicIfNotExists(topic, null);
    }
    void createTopicIfNotExists(String topic, String properties);

    void destroy();

    void deleteTopic(String topic);
}
