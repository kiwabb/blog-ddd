package com.jackmouse.system.iot.queue.scheduler;

import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName DefaultSchedulerComponent
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/8 15:58
 * @Version 1.0
 **/
@Component
public class DefaultSchedulerComponent implements SchedulerComponent{
    @Override
    public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
        return null;
    }
}
