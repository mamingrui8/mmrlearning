package com.mmr.learn.thread.lesson18.t12.tryLock;

import java.util.concurrent.TimeUnit;

public class Run {
    public static void main(String[] args) throws InterruptedException{
        final MyService service = new MyService();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                service.waitMethod();
            }
        };

        Thread threadA = new Thread(runnable);
        threadA.setName("A");
        threadA.start();
        TimeUnit.SECONDS.sleep(2);

        Thread threadB = new Thread(runnable);
        threadB.setName("B");
        threadB.start();
    }
}
