package com.mmr.learn.thread.lesson.lesson18.t10.hasQueuedThread;

import java.util.concurrent.TimeUnit;

public class Run {
    public static void main(String[] args) throws InterruptedException{
        Service service = new Service();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                service.waitMethod();
            }
        };

        Thread threadA = new Thread(runnable);
        threadA.setName("A");
        threadA.start();

        TimeUnit.SECONDS.sleep(1); //让CPU调用线程A，使A陷入深层次睡眠

        Thread threadB = new Thread(runnable);
        threadB.setName("B");
        threadB.start();

        TimeUnit.SECONDS.sleep(1);


        System.out.println("线程A是否正在等待获取锁定: " + service.lock.hasQueuedThread(threadA));
        System.out.println("线程B是否正在等待获取锁定: " + service.lock.hasQueuedThread(threadB));
        System.out.println("是否有线程正在等待获取锁定: " + service.lock.hasQueuedThreads());
    }
}
