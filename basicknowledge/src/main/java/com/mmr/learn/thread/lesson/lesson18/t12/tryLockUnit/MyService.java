package com.mmr.learn.thread.lesson.lesson18.t12.tryLockUnit;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class MyService {
    public ReentrantLock lock = new ReentrantLock();

    public void waitMethod(){
        try {
            if(lock.tryLock(10, TimeUnit.SECONDS)){
                System.out.println(Thread.currentThread().getName() + " 获得锁定的时间是: " + System.currentTimeMillis());
                TimeUnit.SECONDS.sleep(15);
                System.out.println(Thread.currentThread().getName() + " 释放掉锁，当前的时间是: " + System.currentTimeMillis());
            }else{
                System.out.println(Thread.currentThread().getName() + " 没能获得锁, 当前的时间是: " + System.currentTimeMillis());
            }
        }catch(InterruptedException e){
            e.printStackTrace();
        }finally{
            if(lock.isHeldByCurrentThread()){
                lock.unlock();
            }
        }
    }
}
