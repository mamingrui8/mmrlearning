package com.mmr.learn.theory.thinkinginjava.part21;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 创建和管理线程本地存储 ThreadLocal的使用方式
 * 虽然increment()和get()这两个方法都不是同步的，并且共同操作了静态变量value，但由于ThreadLocal将value设置成了本地存储变量的缘故，
 * 保证不会出现竞争的情况。
 * @author Charles Wesley
 * @date 2019/12/15 18:37
 */
public class ThreadLocalVariableHolder {
    private static ThreadLocal<Integer> value = new ThreadLocal<Integer>(){
        private Random rand = new Random(47);
        @Override
        protected synchronized Integer initialValue() {
            return rand.nextInt(10000);
        }
    };

    public static void increment() {
        //ThreadLocal set()是将值插入到了当前线程存储的对象中(value对象的副本)，并返回存储中原有的对象。
        value.set(value.get() + 1);
    }

    public static int get() {
        //ThreadLocal get()返回的是与当前线程相关联的value对象的副本
        return value.get();
    }

    public static void main(String[] args) throws Exception{
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            exec.execute(new Accessor(i));
        }
        TimeUnit.SECONDS.sleep(3);
        exec.shutdown();
    }
}

class Accessor implements Runnable{
    private final int id;
    public Accessor(int idn) {
        this.id = idn;
    }
    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            ThreadLocalVariableHolder.increment();
            System.out.println(this);
            Thread.yield();
        }
    }
    @Override
    public String toString() {
        return "#" + id + ": " + ThreadLocalVariableHolder.get();
    }
}