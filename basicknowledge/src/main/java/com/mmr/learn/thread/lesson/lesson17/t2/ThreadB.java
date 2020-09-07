package com.mmr.learn.thread.lesson.lesson17.t2;

import java.util.concurrent.TimeUnit;

public class ThreadB extends Thread{
    @Override
    public void run(){
        try {
            for (int i = 0; i < 10; i++) {
                Tools.t1.set("ThreadB " + (i + 1));
                System.out.println("ThreadB get Value=" + Tools.t1.get());
                TimeUnit.MILLISECONDS.sleep(200);
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
