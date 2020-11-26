package com.candy.spring.service;

import com.candy.spring.dao.UserDao;

/**
 * @Author:lwt
 * @Description:
 * @Date:Created in 2020/11/26 14:21
 * @Modified:
 */
public class UserServiceImpl implements UserService{

    UserDao userDao;

    @Override
    public void find() {
        userDao.query();
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
