package com.mmr.learn.thread.lesson13.atomicIntegerNoSafe;

import java.util.concurrent.atomic.AtomicLong;

public class MyService {
    public static AtomicLong atomicLong = new AtomicLong();

    /**
     * 先加100再加1
     */
    public void addNum(){
        System.out.println(Thread.currentThread().getName() + " 加了100之后的值是：" + atomicLong.addAndGet(100));
        System.out.println(Thread.currentThread().getName() + " 加了1之后的值是： " + atomicLong.addAndGet(1));
    }
}
