package com.jackmouse.system.utils;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName JackmouseThreadFactory
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/2 11:32
 * @Version 1.0
 **/
public class JackmouseThreadFactory implements ThreadFactory {
    public static final String THREAD_TOPIC_SEPARATOR = " | ";
    private static final AtomicInteger poolNumber = new AtomicInteger(1);
    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;

    public static JackmouseThreadFactory forName(String name) {
        return new JackmouseThreadFactory(name);
    }

    private JackmouseThreadFactory(String name) {
        group = Thread.currentThread().getThreadGroup();
        namePrefix = name + "-" +
                poolNumber.getAndIncrement() +
                "-thread-";
    }

    public static void updateCurrentThreadName(String threadSuffix) {
        String name = Thread.currentThread().getName();
        int spliteratorIndex = name.indexOf(THREAD_TOPIC_SEPARATOR);
        if (spliteratorIndex > 0) {
            name = name.substring(0, spliteratorIndex);
        }
        name = name + THREAD_TOPIC_SEPARATOR + threadSuffix;
        Thread.currentThread().setName(name);
    }

    public static void addThreadNamePrefix(String prefix) {
        String name = Thread.currentThread().getName();
        name = prefix + "-" + name;
        Thread.currentThread().setName(name);
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r,
                namePrefix + threadNumber.getAndIncrement(),
                0);
        if (t.isDaemon())
            t.setDaemon(false);
        if (t.getPriority() != Thread.NORM_PRIORITY)
            t.setPriority(Thread.NORM_PRIORITY);
        return t;
    }
}
