package com.mmr.learn.thread.lesson.lesson18.t9.getQueueLength;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Service {
    public ReentrantLock lock = new ReentrantLock();

    public void serviceMethod1(){
        try {
            lock.lock();
            System.out.println("ThreadName=" + Thread.currentThread().getName() + "进入方法！");
            TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }
}
