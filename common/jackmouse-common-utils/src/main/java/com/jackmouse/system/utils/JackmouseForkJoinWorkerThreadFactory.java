package com.jackmouse.system.utils;

import lombok.NonNull;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @ClassName ThingsBoardForkJoinWorkerThreadFactory
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/1 10:30
 * @Version 1.0
 **/
public class JackmouseForkJoinWorkerThreadFactory implements ForkJoinPool.ForkJoinWorkerThreadFactory {
    private final String namePrefix;
    private final AtomicLong threadNumber = new AtomicLong(1);

    public JackmouseForkJoinWorkerThreadFactory(@NonNull String namePrefix) {
        this.namePrefix = namePrefix;
    }
    @Override
    public ForkJoinWorkerThread newThread(ForkJoinPool pool) {
        ForkJoinWorkerThread thread = ForkJoinPool.defaultForkJoinWorkerThreadFactory.newThread(pool);
        thread.setContextClassLoader(this.getClass().getClassLoader());
        thread.setName(this.namePrefix + "-" + threadNumber.getAndIncrement());
        return thread;
    }
}
