package com.mmr.learn.thread.lesson18.t16;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Service {
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public void read(){
        try {
            lock.readLock().lock();
            System.out.println("获得读锁 " + Thread.currentThread().getName() + " " + System.currentTimeMillis());
            TimeUnit.SECONDS.sleep(10);
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally{
            lock.readLock().lock();
        }
    }
}
