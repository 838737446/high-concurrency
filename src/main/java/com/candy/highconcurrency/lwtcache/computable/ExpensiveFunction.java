package com.candy.highconcurrency.lwtcache.computable;

/**
 * @Author:lwt
 * @Description:
 * @Date:Created in 2020/11/4 16:30
 * @Modified:
 */
public class ExpensiveFunction implements Computable<String, Integer> {

    @Override
    public Integer computer(String arg) throws Exception {
        try {
            Thread.sleep(5000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return Integer.parseInt(arg);
    }
}
