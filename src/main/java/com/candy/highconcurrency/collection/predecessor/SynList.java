package com.candy.highconcurrency.collection.predecessor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 演示collection。synchronizedList
 */
public class SynList {

    public static void main(String[] args) {
        List<String> list = Collections.synchronizedList(new ArrayList<>());
        list.add("张三");
        System.out.println(list.get(0));
    }
}
