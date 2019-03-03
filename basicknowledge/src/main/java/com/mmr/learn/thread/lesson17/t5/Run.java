package com.mmr.learn.thread.lesson17.t5;

import java.util.concurrent.TimeUnit;

public class Run {
    public static void main(String[] args) {
        try {
            for (int i = 0; i < 10; i++) {
                System.out.println("        在main线程中取值=" + Tools.t1.get());
                TimeUnit.MILLISECONDS.sleep(100);
            }
            TimeUnit.SECONDS.sleep(5);

            ThreadA threadA =new ThreadA();
            threadA.start();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
