package com.candy.highconcurrency.threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;

public class ThreadLocalFirstTest02 {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>());
        for (int i = 1; i <= 100; i++) {
            final int tem = i;
            threadPoolExecutor.execute(new TaskDateFormat(tem));
        }
    }

    /**
     * 将秒转换成日期
     *
     * @param second
     * @return
     */
    public String dateFormatSecond(int second) {
        Date data = new Date(1000 * second);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(data);
    }
}
class TaskDateFormat implements Runnable{

    private int tem;

    public TaskDateFormat(int tem) {
        this.tem=tem;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String dateFormatSecond = new ThreadLocalFirstTest02().dateFormatSecond(tem);
        System.out.println("当前线程名称" + Thread.currentThread().getName() + ";转换之后的时间：" + dateFormatSecond);
    }
}
