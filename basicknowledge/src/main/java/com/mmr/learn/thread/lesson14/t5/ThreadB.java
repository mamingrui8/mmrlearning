package com.mmr.learn.thread.lesson14.t5;

import lombok.Synchronized;

import java.util.concurrent.TimeUnit;

public class ThreadB extends Thread{
    private Object lock;

    public ThreadB(Object lock){
        super();
        this.lock = lock;
    }

    @Override
    public void run(){
        try{
            synchronized(lock){
                for (int i = 0; i < 10; i++) {
                    MyList.add();
                    if(MyList.size() == 5){
                        lock.notify();
                        System.out.println("已发出通知");
                    }
                    System.out.println("添加了 " + (i+1) + " 个元素");
                    TimeUnit.SECONDS.sleep(1);
                }
            }
        } catch (InterruptedException e){
           e.printStackTrace();
        }
    }
}
