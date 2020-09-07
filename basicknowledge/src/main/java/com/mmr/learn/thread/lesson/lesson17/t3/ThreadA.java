package com.mmr.learn.thread.lesson.lesson17.t3;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("Duplicates")
public class ThreadA extends Thread{
    @Override
    public void run(){
        try {
            for (int i = 0; i < 20; i++) {
                //System.out.println("A " + Tools.threadLocal2.get());
                if(Tools.threadLocal.get() == null){
                    Tools.threadLocal.set(new Date());
                }
                System.out.println("A " + Tools.threadLocal.get().getTime());
                TimeUnit.MILLISECONDS.sleep(100);
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
