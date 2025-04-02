package com.jackmouse.system.utils;

import org.springframework.util.StopWatch;

/**
 * @ClassName JmStopWatch
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/2 13:10
 * @Version 1.0
 **/
public class JmStopWatch extends StopWatch {
    public static JmStopWatch create(){
        JmStopWatch stopWatch = new JmStopWatch();
        stopWatch.start();
        return stopWatch;
    }
    public static JmStopWatch create(String taskName){
        JmStopWatch stopWatch = new JmStopWatch();
        stopWatch.start(taskName);
        return stopWatch;
    }

    public void startNew(String taskName){
        stop();
        start(taskName);
    }
    public long stopAndGetTotalTimeMillis(){
        stop();
        return getTotalTimeMillis();
    }
    public long stopAndGetTotalTimeNanos(){
        stop();
        return lastTaskInfo().getTimeNanos();
    }

    public long stopAndGetLastTaskTimeMillis(){
        stop();
        return lastTaskInfo().getTimeNanos();
    }

    public long stopAndGetLastTaskTimeNanos(){
        stop();
        return lastTaskInfo().getTimeNanos();
    }

}
