package com.mmr.learn.thread.lesson14.t6;

import java.util.concurrent.TimeUnit;

public class Service {
    public void testMethod(Object lock){
        try {
            synchronized (lock){
                System.out.println("begin wait()");
                //lock.wait();
                TimeUnit.SECONDS.sleep(2);
                System.out.println(" end wait()");
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
