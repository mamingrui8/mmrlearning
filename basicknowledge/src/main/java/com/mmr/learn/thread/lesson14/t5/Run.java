package com.mmr.learn.thread.lesson14.t5;

public class Run {
    public static void main(String[] args) throws InterruptedException{
        Object lock = new Object();
        ThreadA a = new ThreadA(lock);
        a.setName("A");
        a.start();
        ThreadB b = new ThreadB(lock);
        b.setName("B");
        b.start();
    }
}
