package com.mmr.learn.thread.lesson3;

public class Entrance {
    /**
     * lesson3实验结论:
     * 1.线程A先持有共享资源对象object的锁，线程B可以以异步的方式调用object对象中的非synchronized方法。
     * 2.线程A先持有共享资源对象object的锁，线程B是以同步的方式调用object对象中的synchronized方法。
     *   也即，只有当A释放持有object的锁后，线程B才能访问object对象中的synchronized方法。
     */
    public static void main(String[] args){
        MyObject object = new MyObject();
        ThreadA threadA = new ThreadA(object);
        threadA.setName("A");
        ThreadB threadB = new ThreadB(object);
        threadB.setName("B");

        threadA.start();
        threadB.start();
    }
}
