package com.mmr.learn.thread.lesson18.t5;

import java.util.concurrent.TimeUnit;

public class Run {
    public static void main(String[] args) throws InterruptedException {
        MyService service = new MyService();

        ThreadA a = new ThreadA(service);
        a.setName("A");
        a.start();

        ThreadB b = new ThreadB(service);
        b.setName("B");
        b.start();

        TimeUnit.SECONDS.sleep(3);
        service.signalAll_A();

        TimeUnit.MILLISECONDS.sleep(300);

        System.out.println("threadA->" + a.isAlive());
        System.out.println("threadB->" + b.isAlive());
    }
}
