package com.jackmouse.system.utils;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

/**
 * @ClassName JackmouseScheduledThreadPoolExecutor
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/3 09:03
 * @Version 1.0
 **/
public class JackmouseScheduledThreadPoolExecutor extends ScheduledThreadPoolExecutor {

    public JackmouseScheduledThreadPoolExecutor(int corePoolSize, ThreadFactory threadFactory) {
        super(corePoolSize, threadFactory);
    }
}
