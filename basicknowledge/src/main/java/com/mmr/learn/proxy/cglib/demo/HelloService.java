package com.mmr.learn.proxy.cglib.demo;

/**
 * 具体主题角色
 *
 * @author mamr
 * @date 2020/9/6 18:26
 */
public class HelloService {
    public HelloService() {
        System.out.println("HelloService的构造函数");
    }

    final public String test1() {
        System.out.println("HelloService:test1 " + "不能被自类覆盖的方法");
        return "test1";
    }

    public void test2() {
        System.out.println("HelloService:test2");
    }

    public String test3() {
        //System.out.println("HelloService:test3");
        return "HelloService:test3";
    }
}
