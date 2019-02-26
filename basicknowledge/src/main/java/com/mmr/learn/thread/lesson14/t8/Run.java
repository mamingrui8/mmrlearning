package com.mmr.learn.thread.lesson14.t8;

import java.util.concurrent.TimeUnit;

public class Run {
    public static void main(String[] args){
        try {
            Object lock = new Object();
            ThreadA a = new ThreadA(lock);
            a.setName("A");

            ThreadB b = new ThreadB(lock);
            b.setName("B");
            b.setPriority(Thread.MAX_PRIORITY);

            ThreadC c = new ThreadC(lock);
            c.setName("C");

            a.start();
            b.start();
            c.start();

            TimeUnit.SECONDS.sleep(1);
            NotifyThread notifyThread = new NotifyThread(lock);
            notifyThread.start();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
