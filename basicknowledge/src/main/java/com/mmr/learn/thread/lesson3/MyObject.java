package com.mmr.learn.thread.lesson3;

import java.util.concurrent.TimeUnit;

public class MyObject {
    synchronized public void methodA(){
        try {
            System.out.println("begin methodA threadName=" + Thread.currentThread().getName());
            TimeUnit.MILLISECONDS.sleep(5000);
            System.out.println("end endTime=" + System.currentTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void methodB(){
        try {
            System.out.println("begin methodB threadName=" + Thread.currentThread().getName()
                            + " begin time=" + System.currentTimeMillis());
            TimeUnit.MILLISECONDS.sleep(5000);
            System.out.println("end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


