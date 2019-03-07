package com.mmr.learn.thread.lesson18.t10.hasWaiter;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Service {
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void waitConditionMethod(){
        try {
            lock.lock();
            condition.await();
        }catch(InterruptedException e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }

    public void notifyConditionMethod(){
        try {
            lock.lock();
            System.out.println("当前是否有线程正在等待Condition条件的实现以此来获取锁定呢? 答:" + lock.hasWaiters(condition));
            System.out.println("等待Condition的线程有多少个呢？ 答: " + lock.getWaitQueueLength(condition));
            condition.signal();
        }finally{
            lock.unlock();
        }

    }
}
