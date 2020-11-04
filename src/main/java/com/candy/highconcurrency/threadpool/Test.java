package com.candy.highconcurrency.threadpool;

public class Test {
    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            Thread thread = new Thread(new Task());
            thread.start();
        }
    }

    static class Task implements Runnable {
        @Override
        public void run() {
            System.out.println("当前线程的名称：" + Thread.currentThread().getName());
        }
    }
}
