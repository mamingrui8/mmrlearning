package com.mmr.learn.proxy;

/**
 * @author mamr
 * @date 2020/11/16 9:29 下午
 */
public class UserServiceImpl implements UserService {
    public void select() {
        System.out.println("查询 selectById");
    }
    public void update() {
        System.out.println("更新 update");
    }
}
