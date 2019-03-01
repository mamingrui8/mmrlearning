package com.mmr.learn.thread.lesson16.t3;

import java.util.concurrent.TimeUnit;

public class ThreadB extends Thread{
    @Override
    public void run(){
        try {
            System.out.println("b run begin timer=" + System.currentTimeMillis());
            TimeUnit.SECONDS.sleep(5);
            System.out.println("b run end timer=" + System.currentTimeMillis());
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    synchronized public void bService(){
        System.out.println("打印了bService timer=" + System.currentTimeMillis());
    }
}
