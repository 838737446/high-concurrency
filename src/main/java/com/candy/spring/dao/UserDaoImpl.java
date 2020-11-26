package com.candy.spring.dao;

/**
 * @Author:lwt
 * @Description:
 * @Date:Created in 2020/11/26 14:21
 * @Modified:
 */
public class UserDaoImpl implements UserDao{

    @Override
    public void query() {
        System.out.println("模拟用户查询数据库");
    }
}
