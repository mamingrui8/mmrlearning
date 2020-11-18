package com.mmr.learn.proxy.cglib;


/**
 * 需要实现CGLIB动态代理的类
 *
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

    public void unEnhance() {
        System.out.println("UserDao unEnhance");
    }

    // 绝对不能覆盖无参构造函数  因为cglib在通过Method invoke()时，必须传递一个对象，而对象就是通过无参构造函数创建出来的
//    public UserDao(String ss) {
//        System.out.println(ss);
//    }
}
