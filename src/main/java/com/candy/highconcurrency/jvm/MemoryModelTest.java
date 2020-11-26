package com.candy.highconcurrency.jvm;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Author:lwt
 * @Description:
 * @Date:Created in 2020/11/6 9:29
 * @Modified:
 */
public class MemoryModelTest {

    private int add() {
        int a = 1;
        int b = 2;
        int c = (a + b) * 10;
        System.out.println(c);
        return c;
    }

    public void test(){
        List<String> strings=new ArrayList<>();

    }

    public static void main(String[] args) {
        MemoryModelTest memoryModelTest = new MemoryModelTest();
//        memoryModelTest.add();
        while(true) {
//            MemoryModelTest memoryModelTest=new MemoryModelTest();
            memoryModelTest.test();
            System.out.println("1");
        }
    }
}
