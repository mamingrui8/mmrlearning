package com.mmr.learn.thread.lesson14.t7;

import java.util.concurrent.TimeUnit;

public class Run {
    public static void main(String[] args){
        try {
            Object lock = new Object();
            ThreadA a = new ThreadA(lock);
            a.start();
            TimeUnit.SECONDS.sleep(5);
            a.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
