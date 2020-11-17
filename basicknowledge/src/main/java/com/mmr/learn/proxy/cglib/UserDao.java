package com.mmr.learn.proxy.cglib;

/**
 * 需要实现CGLIB动态代理的类
 * @author mamr
 * @date 2020/11/17 22:49
 */
public class UserDao {
    public void select() {
        System.out.println("UserDao 查询 selectById");
    }

    public void update() {
        System.out.println("UserDao 更新 update");
    }
}
