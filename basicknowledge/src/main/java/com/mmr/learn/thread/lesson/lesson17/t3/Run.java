package com.mmr.learn.thread.lesson.lesson17.t3;

import java.util.concurrent.TimeUnit;

public class Run {
    public static void main(String[] args) {
        try {
            ThreadA threadA = new ThreadA();
            threadA.setName("A");
            threadA.start();
            TimeUnit.SECONDS.sleep(1);

            ThreadB threadB = new ThreadB();
            threadB.setName("B");
            threadB.start();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
