package com.jackmouse.system.iot.queue.memory;

import com.jackmouse.system.iot.queue.JmQueueMsg;

import java.util.List;

/**
 * @ClassName InMemoryStorage
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/2 09:02
 * @Version 1.0
 **/
public interface InMemoryStorage<T extends JmQueueMsg> {
    void printStats();
    int getLagTotal();
    int getLag(String topic);
    boolean put(String topic, JmQueueMsg msg);
    List<T> get(String topic) throws InterruptedException;
}
