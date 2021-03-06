package com.mmr.learn.thread.lesson.lesson18.t999;

import java.util.concurrent.TimeUnit;

public class Run {
    public static void main(String[] args) throws InterruptedException {
        MyService service = new MyService();

        ThreadA threadA = new ThreadA(service);
        threadA.start();

        TimeUnit.SECONDS.sleep(2);
        ThreadB threadB = new ThreadB(service);
        threadB.start();
    }

}
