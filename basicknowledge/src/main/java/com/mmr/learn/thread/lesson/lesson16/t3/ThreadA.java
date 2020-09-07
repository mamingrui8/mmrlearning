package com.mmr.learn.thread.lesson.lesson16.t3;

import java.util.concurrent.TimeUnit;

public class ThreadA extends Thread{
    private ThreadB threadB;

    public ThreadA(ThreadB threadB){
        super();
        this.threadB = threadB;
    }

    @Override
    public void run(){
        try {
            synchronized (threadB){
                threadB.start();
                TimeUnit.SECONDS.sleep(6);
                //TimeUnit.SECONDS.sleep(6); 不释放锁！
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
