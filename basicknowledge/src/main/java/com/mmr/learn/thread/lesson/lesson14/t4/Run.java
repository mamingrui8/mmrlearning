package com.mmr.learn.thread.lesson.lesson14.t4;

import java.util.concurrent.TimeUnit;

public class Run {
    public static void main(String[] args){
        try {
            Object lock  = new Object();
            ThreadA a = new ThreadA(lock);
            a.setName("A");
            a.start();
            TimeUnit.SECONDS.sleep(3);
            ThreadB b = new ThreadB(lock);
            b.start();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
