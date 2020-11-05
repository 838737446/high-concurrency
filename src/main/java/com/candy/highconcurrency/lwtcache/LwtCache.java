package com.candy.highconcurrency.lwtcache;

import com.candy.highconcurrency.lwtcache.computable.Computable;
import com.candy.highconcurrency.lwtcache.computable.ExpensiveFunction;
import com.candy.highconcurrency.lwtcache.computable.MayFail;
import org.springframework.http.converter.json.GsonBuilderUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @Author:lwt
 * @Description:
 * @Date:Created in 2020/11/4 15:03
 * @Modified:
 */
public class LwtCache<A, V> {

    /**
     * 用于存储缓存数据，使用ConcurrentHashMap保证线程安全
     */
    private final Map<A, Future<V>> cache = new ConcurrentHashMap<>();

    private final Computable<A, V> c;

    public LwtCache(Computable<A, V> c) {
        this.c = c;
    }

    public final static ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);

    /**
     * 获取缓存
     *
     * @param userId
     * @return
     */
    private V getCache(A userId) throws Exception {
        while (true) {
//            System.out.println(userId + "正在获取缓存数据");
            Future<V> result = cache.get(userId);
            if (result == null) {
                Callable<V> callable = () -> {
                    return c.computer(userId);
                };
                FutureTask<V> futureTask = new FutureTask(callable);
                synchronized (this) {
                    result = cache.putIfAbsent(userId, futureTask);
                    if (result == null) {
                        result = futureTask;
                        futureTask.run();
                    }
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

    /**
     * 获取带过期时间的缓存
     *
     * @param userId
     * @param expire 过期时间 单位毫秒
     * @return
     * @throws Exception
     */
    private V getCache(A userId, long expire) throws Exception {
        if (expire > 0) {
            executor.schedule(() -> expire(userId), expire, TimeUnit.MILLISECONDS);
        }
        return getCache(userId);
    }

    /**
     * 设置随机过期时间
     *
     * @param userId
     * @return
     * @throws Exception
     */
    private V getCacheRandomExpire(A userId) throws Exception {
        long expire = (long) (Math.random() * 10000);
        executor.schedule(() -> expire(userId), expire, TimeUnit.MILLISECONDS);
        return getCache(userId);
    }

    public synchronized void expire(A key) {
        Future<V> future = cache.get(key);
        if (future != null) {
            if (!future.isDone()) {
                System.out.println("Future任务被取消");
                future.cancel(true);
            }
            System.out.println(key + "过期时间到，缓存被清除" + new SimpleDateFormat("mm:ss").format(new Date()));
            cache.remove(key);
        }
    }

    public static void main(String[] args) throws Exception {
        LwtCache<String, Integer> lwtCache = new LwtCache(new ExpensiveFunction());
//        LwtCache<String,Integer> lwtCache = new LwtCache(new MayFail());
//        System.out.println(lwtCache.getCache("222"));
//        System.out.println(lwtCache.getCache("333"));
//        System.out.println(lwtCache.getCache("222"));
//        new Thread(() -> {
//            try {
//                Integer result = lwtCache.getCache("666", 10000);
//                System.out.println("第一次的计算结果：" + result);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }).start();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Integer result = lwtCache.getCacheRandomExpire("666");
//                    System.out.println("第三次的计算结果：" + result);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Integer result = lwtCache.getCacheRandomExpire("667");
//                    System.out.println("第二次的计算结果：" + result);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ExecutorService executorService = Executors.newFixedThreadPool(1000);
        for (int i = 0; i < 1000; i++) {
            executorService.submit(() -> {
                try {
                    countDownLatch.await();
                    Integer result = lwtCache.getCache("666");
                    SimpleDateFormat simpleDateFormat = SimpleDateFormatThreadLocal.dateformat.get();
                    System.out.println(result + "获取时间：" + simpleDateFormat.format(new Date()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        countDownLatch.countDown();
        executorService.shutdown();
    }
}

class SimpleDateFormatThreadLocal {
    public static ThreadLocal<SimpleDateFormat> dateformat = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("mm:ss");
        }

        @Override
        public SimpleDateFormat get() {
            return super.get();
        }
    };
}
