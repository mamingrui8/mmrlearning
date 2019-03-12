package com.mmr.learn.jvm.t2;

import java.util.concurrent.locks.ReentrantLock;

public class MyService {
    //堆
    private ReentrantLock lock = new ReentrantLock();

    private String slogan;

    private Integer firstFlag;

    private double secondFlag;

    /**
     * 私有 无返回值
     */
    private void method1(){

    }

    /**
     * 共有 无返回值
     */
    public void method2(){

    }


}
