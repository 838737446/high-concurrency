package com.candy.highconcurrency.collection.predecessor;

import java.util.HashMap;
import java.util.Map;

/**
 * 演示Map的基本用法
 */
public class MapDemo {
    public static void main(String[] args) {
        Map<String,Integer> map=new HashMap<>();
        map.put("东哥",38);
        map.put("系哥",40);
        System.out.println("打印出keys所有集合"+ map.keySet());
    }
}
