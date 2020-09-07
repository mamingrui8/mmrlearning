package com.mmr.learn.thread.lesson.lesson18.t4;

import java.util.concurrent.TimeUnit;

public class Run {
    public static void main(String[] args) throws InterruptedException{
        MyService service = new MyService();

        ThreadA a = new ThreadA(service);
        a.start();

        TimeUnit.SECONDS.sleep(3);

        service.signal();
    }
}
