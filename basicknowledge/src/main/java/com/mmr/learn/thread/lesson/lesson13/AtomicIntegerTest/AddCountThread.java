package com.mmr.learn.thread.lesson.lesson13.AtomicIntegerTest;

import java.util.concurrent.atomic.AtomicInteger;

public class AddCountThread extends Thread{
    private static AtomicInteger count = new AtomicInteger(0);

    @Override
    public void run(){
        for(int i=0; i<10000; i++){
            System.out.println(count.incrementAndGet());
        }
    }
}
