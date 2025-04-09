package com.jackmouse.system.iot.queue.scheduler;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName SchedulerComponent
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/7 16:21
 * @Version 1.0
 **/
public interface SchedulerComponent {
    ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit);
}
