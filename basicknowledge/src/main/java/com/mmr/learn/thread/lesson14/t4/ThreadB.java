package com.mmr.learn.thread.lesson14.t4;

import java.util.concurrent.TimeUnit;

public class ThreadB extends Thread{
    private Object lock;

    public ThreadB(Object lock){
        super();
        this.lock = lock;
    }

    @Override
    public void run(){
        synchronized (lock){
            System.out.println("开始   notify time=" + System.currentTimeMillis());
            lock.notify();
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("结束   notify time=" + System.currentTimeMillis());
        }
    }
}
