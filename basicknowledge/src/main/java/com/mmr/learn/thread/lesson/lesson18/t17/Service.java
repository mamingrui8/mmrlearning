package com.mmr.learn.thread.lesson.lesson18.t17;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Service {
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public void write(){
        try {
            lock.writeLock().lock();
            System.out.println("获得写锁 " + Thread.currentThread().getName() + " " + System.currentTimeMillis());
            TimeUnit.SECONDS.sleep(10);
        }catch(InterruptedException e){
            e.printStackTrace();
        }finally{
            lock.writeLock().unlock();
        }
    }
}
