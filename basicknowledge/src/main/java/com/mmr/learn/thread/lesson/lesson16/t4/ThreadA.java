package com.mmr.learn.thread.lesson.lesson16.t4;

import java.util.concurrent.TimeUnit;

public class ThreadA extends Thread{
    private ThreadB b;

    public ThreadA(ThreadB b){
        super();
        this.b = b;
    }

    @Override
    public void run(){
        try {
            synchronized(b){
                System.out.println("begin A ThreadName=" + Thread.currentThread().getName() + "  " + System.currentTimeMillis());
                TimeUnit.SECONDS.sleep(5);
                System.out.println("end A ThreadName=" + Thread.currentThread().getName() + "  " + System.currentTimeMillis());
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
