package com.mmr.learn.thread.lesson.lesson13.atomicIntegerNoSafe;

import java.util.concurrent.TimeUnit;

public class Run {
    public static void main(String[] args){
        try{
            MyService service = new MyService();
            MyThread[] arrays = new MyThread[5];
            for(int i=0; i< arrays.length; i++){
                arrays[i] = new MyThread(service);
            }
            for(int i=0; i< arrays.length; i++){
                arrays[i].start();
            }
            TimeUnit.SECONDS.sleep(1);
            System.out.println(service.atomicLong.get());
        } catch (InterruptedException e){
           e.printStackTrace();
        }
    }
}
