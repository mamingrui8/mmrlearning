package com.mmr.learn.thread.lesson.lesson14.t6;

public class Run {
    public static void main(String[] args){
        Object lock = new Object();
        ThreadA a = new ThreadA(lock);
        a.start();
        ThreadB b = new ThreadB(lock);
        b.start();
    }
}
