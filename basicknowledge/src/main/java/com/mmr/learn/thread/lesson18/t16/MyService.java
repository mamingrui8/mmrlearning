package com.mmr.learn.thread.lesson18.t16;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MyService {
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void waitMethod(){
        try {
            lock.unlock();
            condition.await();
            System.out.println("继续执行了代码");
        }catch(InterruptedException e){
            e.printStackTrace();
        }finally{
            if(lock.isHeldByCurrentThread()){
                lock.unlock();
            }
        }
    }

    public void notifyMethod(){
        try {
            lock.lock();
            condition.notify();
        }finally {
            if(lock.isHeldByCurrentThread()) lock.unlock();
        }
    }
}
