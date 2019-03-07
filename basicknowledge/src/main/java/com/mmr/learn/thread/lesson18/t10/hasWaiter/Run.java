package com.mmr.learn.thread.lesson18.t10.hasWaiter;

import java.util.concurrent.TimeUnit;

@SuppressWarnings("Duplicates")
public class Run {
    public static void main(String[] args) throws InterruptedException{
        final Service service = new Service();
        Runnable runnable = new Runnable(){
            @Override
            public void run(){
                service.waitConditionMethod();
            }
        };

        Thread[] threads = new Thread[10];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(runnable);
        }

        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }

        TimeUnit.SECONDS.sleep(2);

        service.notifyConditionMethod();
    }
}
