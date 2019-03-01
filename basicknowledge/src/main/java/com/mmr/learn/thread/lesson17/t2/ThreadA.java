package com.mmr.learn.thread.lesson17.t2;

import java.util.concurrent.TimeUnit;

public class ThreadA extends Thread{

    @Override
    public void run(){
        try {
            for (int i = 0; i < 10; i++) {
                Tools.t1.set("ThreadA " + (i + 1));
                System.out.println("ThreadA get Value=" + Tools.t1.get());
                TimeUnit.MILLISECONDS.sleep(200);
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
