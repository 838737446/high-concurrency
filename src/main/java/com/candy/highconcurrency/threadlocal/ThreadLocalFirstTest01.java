package com.candy.highconcurrency.threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ThreadLocalFirstTest01 {
    public static void main(String[] args) {
        for (int i=1 ;i<=100; i++){
            final int tem=i;
            Thread thread=new Thread(new Runnable() {
                @Override
                public void run() {
                    String dateFormatSecond=new ThreadLocalFirstTest01().dateFormatSecond(tem);
                    System.out.println("当前线程名称"+Thread.currentThread().getName()+";转换之后的时间："+dateFormatSecond);
                }
            });
            thread.start();
        }
    }

    /**
     * 将秒转换成日期
     * @param second
     * @return
     */
    public String dateFormatSecond(int second){
        Date data=new Date(1000*second);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(data);
    }
}
