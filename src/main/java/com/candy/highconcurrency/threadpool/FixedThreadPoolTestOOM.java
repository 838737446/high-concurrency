package com.candy.highconcurrency.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FixedThreadPoolTestOOM {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        for(int i=0;i<Integer.MAX_VALUE;i++){
            executorService.execute(new SubTask());
        }
    }
}
class SubTask implements Runnable{

    @Override
    public void run() {
        try {
            Thread.sleep(100000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("当前线程的名称"+Thread.currentThread().getName());
    }
}
