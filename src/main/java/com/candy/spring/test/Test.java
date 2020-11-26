package com.candy.spring.test;

import com.candy.spring.service.UserService;
import com.candy.spring.util.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author:lwt
 * @Description:
 * @Date:Created in 2020/11/26 14:21
 * @Modified:
 */
public class Test {
    public static void main(String[] args) {
        //spring依赖注入演示
//        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("spring.xml");
//        UserService userService=(UserService) applicationContext.getBean("userService");
//        userService.find();
        //自定义spring
        BeanFactory beanFactory=new BeanFactory("springFrameWork.xml");
    }
}
