package com.candy.highconcurrency.threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author lwt
 */
public class ThreadLocalFirstTest03 {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(50, 50, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>());
        for (int i = 1; i <= 10000; i++) {
            final int tem = i;
            threadPoolExecutor.execute(() -> {
                String dateFormatSecond = new ThreadLocalFirstTest03().dateFormatSecond(tem);
                System.out.println("当前线程名称" + Thread.currentThread().getName() + ";转换之后的时间：" + dateFormatSecond);
            });
        }
    }

    /**
     * 将秒转换成日期
     *
     * @param second
     * @return
     */
    public String dateFormatSecond(int second) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Date data = new Date(1000 * second);
        return sdf.format(data);
    }
}
