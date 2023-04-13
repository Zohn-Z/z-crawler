package com.zzz.proxypool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @description: 定时任务抽象类
 * @author: 张宗正
 * @since: 2023/4/10 14:12
 **/
public abstract class Scheduler implements Runnable {

    protected long initialDelay = 0;
    protected long period = 3;
    protected TimeUnit unit = TimeUnit.HOURS;
    protected ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    public Scheduler() {

    }

    public Scheduler(long initialDelay, long period, TimeUnit unit, ScheduledExecutorService scheduledExecutorService) {
        this.initialDelay = initialDelay;
        this.period = period;
        this.unit = unit;
        this.scheduledExecutorService = scheduledExecutorService;
    }

    public void start() {
        scheduledExecutorService.scheduleAtFixedRate(this, initialDelay, period, unit);
    }
}
