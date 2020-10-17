package com.candy.highconcurrency.threadlocal;

import lombok.Builder;
import lombok.Data;

/**
 * @author Administrator
 * description ThreadLocal解决参数传递问题
 */
public class ThreadLocalSecondTest01 {

    ThreadLocal<User> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {

        ThreadLocalSecondTest01 threadLocalSecondTest01 = new ThreadLocalSecondTest01();
        threadLocalSecondTest01.process01();
    }

    public void set(User user) {
        threadLocal.set(user);
    }

    public User get() {
        return threadLocal.get();
    }

    public void process01() {
        User user=User.builder().name("张三").build();
        set(user);
        System.out.println("调用了process01");
        process02();
    }

    public void process02() {
        User user=User.builder().name("李四").build();
        set(user);
        System.out.println("调用了process02 姓名："+user.getName());
        process03();
    }

    public void process03() {
        User user=get();
        System.out.println("调用了process03 姓名："+user.getName());
    }

}

@Data
@Builder
class User {
    String name;
}
