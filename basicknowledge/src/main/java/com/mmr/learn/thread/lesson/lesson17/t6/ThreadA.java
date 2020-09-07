package com.mmr.learn.thread.lesson.lesson17.t6;

import java.util.concurrent.TimeUnit;

public class ThreadA extends Thread{
    @Override
    public void run(){
        try {
            for (int i = 0; i < 10; i++) {
                System.out.println("在ThreadA中获取值 = " + Tools.t1.get());
                TimeUnit.MILLISECONDS.sleep(100);
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
