package com.mmr.learn.thread.lesson18.t14;

import java.util.concurrent.TimeUnit;

public class Run {
    public static void main(String[] args) throws InterruptedException{
        Service service = new Service();
        MyThreadA threadA = new MyThreadA(service);
        threadA.start();

        TimeUnit.SECONDS.sleep(2);
        MyThreadB threadB = new MyThreadB(service);
        threadB.start();
    }
}
