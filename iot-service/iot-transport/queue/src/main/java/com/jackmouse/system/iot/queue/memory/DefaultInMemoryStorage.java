package com.jackmouse.system.iot.queue.memory;

import com.jackmouse.system.iot.queue.JmQueueMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @ClassName DefaultInMemoryStorage
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/2 09:05
 * @Version 1.0
 **/
@Component
@Slf4j
public class DefaultInMemoryStorage implements InMemoryStorage<JmQueueMsg>{

    private final ConcurrentHashMap<String, BlockingQueue<JmQueueMsg>> storage = new ConcurrentHashMap<>();

    @Override
    public void printStats() {
        if (log.isDebugEnabled()) {
            storage.forEach((topic, queue) -> {
                if (!queue.isEmpty()) {
                    log.debug("[{}] Queue Size [{}]", topic, queue.size());
                }
            });
        }
    }

    @Override
    public int getLagTotal() {
        return storage.values().stream().mapToInt(BlockingQueue::size).sum();
    }

    @Override
    public int getLag(String topic) {
        return Optional.ofNullable(storage.get(topic)).map(Collection::size).orElse(0);
    }

    @Override
    public boolean put(String topic, JmQueueMsg msg) {
        return storage.computeIfAbsent(topic, k -> new LinkedBlockingQueue<>()).add(msg);
    }


    @Override
    public List<JmQueueMsg> get(String topic) throws InterruptedException {
        final BlockingQueue<JmQueueMsg> queue = storage.get(topic);
        if (queue != null) {
            final JmQueueMsg first = queue.poll();
            if (first != null) {
                final int queueSize = queue.size();
                if (queueSize > 0) {
                    final List<JmQueueMsg> entities = new ArrayList<>(Math.max(queueSize, 999) + 1);
                    queue.drainTo(entities);
                    return entities;
                }
                return Collections.singletonList(first);
            }

        }
        return Collections.emptyList();
    }




}
