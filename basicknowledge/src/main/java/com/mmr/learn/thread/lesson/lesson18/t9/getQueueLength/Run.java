package com.mmr.learn.thread.lesson.lesson18.t9.getQueueLength;

import java.util.concurrent.TimeUnit;

public class Run {
    public static void main(String[] args){
        try {
            Service service = new Service();

            Runnable runnable = new Runnable(){
                @Override
                public void run(){
                    service.serviceMethod1();
                }
            };

            Thread[] threads = new Thread[10];
            for (int i = 0; i < 10; i++) {
                threads[i] = new Thread(runnable);
            }
            for (int i = 0; i < 10; i++) {
                threads[i].start();
            }

            TimeUnit.SECONDS.sleep(2);
            System.out.println("截至目前，有" + service.lock.getQueueLength() + "个线程正在等待获取锁！");
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
