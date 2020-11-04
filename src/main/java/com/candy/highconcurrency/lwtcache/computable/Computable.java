package com.candy.highconcurrency.lwtcache.computable;

/**
 * @Author:lwt
 * @Description: 定义一个计算的标准，有其他类型的计算直接实现对应的方法即可
 * @Date:Created in 2020/11/4 16:18
 * @Modified:
 */
public interface Computable<A,V> {

    /**
     * 用于计算的接口标准
     * @param arg
     * @return
     * @throws Exception
     */
    V computer(A arg) throws Exception;
}
