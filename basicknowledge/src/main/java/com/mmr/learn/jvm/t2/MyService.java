package com.mmr.learn.jvm.t2;

import java.util.concurrent.locks.ReentrantLock;

public class MyService {
    //堆
    private ReentrantLock lock = new ReentrantLock();

    //slogan符号引用对象被放在堆中，它所指的字面量对象来自运行时常量池。
    private String slogan;

    private Integer firstFlag;

    //堆  因为secondFlag不会独立于类的实例存在，而类的实例被初始化后放在了堆中，因此它的属性secondFlag也被放在了堆中
    private double secondFlag;

    //对象s被存放在方法区
    public static String s = "123";

    //pp被存放在方法区， 23 在代码加载后会被存放在运行时常量池中
    private static final int  pp = 23;

    public Student student;
    /**
     * 私有 无返回值
     */
    private void method1(){

    }

    /**
     * 共有 无返回值
     */
    public void method2(){
        String str1 = "123";
    }

}

class Student{

}