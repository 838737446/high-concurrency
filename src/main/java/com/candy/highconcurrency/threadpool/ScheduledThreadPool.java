package com.candy.highconcurrency.threadpool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPool {
    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(20);
        //scheduledExecutorService.schedule(new Task(),5, TimeUnit.SECONDS);
        scheduledExecutorService.scheduleAtFixedRate(new Task(),5,2, TimeUnit.SECONDS);
    }
}
