package com.jackmouse.system.iot.transport;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jackmouse.system.iot.queue.discovery.JmServiceInfoProvider;
import com.jackmouse.system.utils.JackmouseExecutor;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutorService;

/**
 * @ClassName TransportContext
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/1 10:22
 * @Version 1.0
 **/
public abstract class TransportContext {
    ObjectMapper objectMapper = new ObjectMapper();

    private ExecutorService executor;

    @Autowired
    @Getter
    private TransportService transportService;

    @Autowired
    private JmServiceInfoProvider serviceInfoProvider;

    @PostConstruct
    public void init() {
        executor = JackmouseExecutor.newWorkStealingPool(50, getClass());
    }

    @PreDestroy
    public void destroy() {
        if (executor != null) {
            executor.shutdownNow();
        }
    }

    public String getNodeId() {
        return serviceInfoProvider.getServiceId();
    }
}
