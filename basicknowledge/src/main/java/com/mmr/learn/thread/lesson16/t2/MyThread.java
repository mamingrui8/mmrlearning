package com.mmr.learn.thread.lesson16.t2;

import java.util.concurrent.TimeUnit;

public class MyThread extends Thread{
    @Override
    public void run(){
        try {
            System.out.println("begin Timer=" + System.currentTimeMillis());
            TimeUnit.SECONDS.sleep(5);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
