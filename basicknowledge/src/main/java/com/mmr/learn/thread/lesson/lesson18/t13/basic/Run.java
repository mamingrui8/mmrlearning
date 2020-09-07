package com.mmr.learn.thread.lesson.lesson18.t13.basic;

import java.util.concurrent.TimeUnit;

public class Run {
    public static void main(String[] args) {
        try {
            Service service = new Service();
            MyThread thread = new MyThread(service);
            thread.start();
            TimeUnit.SECONDS.sleep(3);

            thread.interrupt();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
