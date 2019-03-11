package com.mmr.learn.thread.lesson18.t13.basic;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Service {
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void testMethod(){
        try {
            if(lock.tryLock()){
                System.out.println(Thread.currentThread().getName() + "线程获得锁，开始等待");
                condition.await();
                System.out.println(Thread.currentThread().getName() + "线程等待结束");
            }else{
                System.out.println("很遗憾，" + Thread.currentThread().getName() + " 线程没能获得锁");
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
