package com.jackmouse.system.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @ClassName JackmouseExecutor
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/1 10:28
 * @Version 1.0
 **/
public class JackmouseExecutor {
    public static ExecutorService newWorkStealingPool(int parallelism, Class clazz) {
        return newWorkStealingPool(parallelism, clazz.getSimpleName());
    }
    public static ExecutorService newWorkStealingPool(int parallelism, String namePrefix) {
        return new ForkJoinPool(parallelism,
                new JackmouseForkJoinWorkerThreadFactory(namePrefix),
                null, true);
    }

    public static ScheduledExecutorService newSingleThreadScheduledExecutor(String name) {
        return Executors.unconfigurableScheduledExecutorService(new JackmouseScheduledThreadPoolExecutor(1, JackmouseThreadFactory.forName(name)));
    }
}
