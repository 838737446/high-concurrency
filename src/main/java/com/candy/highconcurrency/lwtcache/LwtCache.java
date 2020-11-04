package com.candy.highconcurrency.lwtcache;

import com.candy.highconcurrency.lwtcache.computable.Computable;
import com.candy.highconcurrency.lwtcache.computable.ExpensiveFunction;
import com.candy.highconcurrency.lwtcache.computable.MayFail;

import java.util.concurrent.*;

/**
 * @Author:lwt
 * @Description:
 * @Date:Created in 2020/11/4 15:03
 * @Modified:
 */
public class LwtCache<A,V> {

    /**
     * 用于存储缓存数据，使用ConcurrentHashMap保证线程安全
     */
    private final static ConcurrentHashMap<String, Future<Integer>> cache = new ConcurrentHashMap<>();

    private final Computable<String, Integer> c;

    public LwtCache(Computable<String, Integer> c) {
        this.c = c;
    }

    public final static ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);

    /**
     * 获取缓存
     *
     * @param userId
     * @return
     */
    private Integer getCache(String userId) throws Exception {
        while (true) {
            System.out.println(userId + "正在获取缓存数据");
            Future<Integer> result = cache.get(userId);
            if (result == null) {
                Callable<Integer> callable = () -> c.computer(userId);
                FutureTask<Integer> futureTask = new FutureTask(callable);
                result = cache.putIfAbsent(userId, futureTask);
                if (result == null) {
                    result = futureTask;
                    cache.put(userId, futureTask);
                    System.out.println(userId + "FutureTask计算了函数");
                    futureTask.run();
                }
            }
            try {
                return result.get();
            } catch (InterruptedException e) {
                System.out.println("被中断了");
                cache.remove(userId);
                throw e;
            } catch (CancellationException e) {
                System.out.println("被取消了");
                cache.remove(userId);
                throw e;
            } catch (ExecutionException e) {
                cache.remove(userId);
                System.out.println("计算错误，进行重试");
            }
        }
    }

    private Integer getCache(String userId,long expire) throws Exception {
        if(expire>0){
            executor.schedule(() -> expire(userId),expire,TimeUnit.MILLISECONDS);
        }
        return getCache(userId);
    }

    public synchronized void expire(String key) {
        Future<Integer> future = cache.get(key);
        if (future != null) {
            if (!future.isDone()) {
                System.out.println("Future任务被取消");
                future.cancel(true);
            }
            System.out.println("过期时间到，缓存被清除");
            cache.remove(key);
        }
    }

    public static void main(String[] args) throws Exception {
        LwtCache lwtCache = new LwtCache(new MayFail());
//        System.out.println(lwtCache.getCache("222"));
//        System.out.println(lwtCache.getCache("333"));
//        System.out.println(lwtCache.getCache("222"));
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Integer result = lwtCache.getCache("666",2000);
                    System.out.println("第一次的计算结果："+result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Integer result = lwtCache.getCache("666");
                    System.out.println("第三次的计算结果："+result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Integer result = lwtCache.getCache("667");
                    System.out.println("第二次的计算结果："+result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
