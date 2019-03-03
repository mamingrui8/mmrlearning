package com.mmr.learn.thread.lesson18.t6;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyService {
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private boolean hasValue = false;

    public void setValue(){
        try {
            System.out.println("你好->" + Thread.currentThread().getName());
            lock.lock();
            if(hasValue) condition.await();
            System.out.println("☆");
            hasValue = true;
            condition.signal();
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally{
            System.out.println("释放锁");
            lock.unlock();
        }
    }

    public void getValue(){
        try {
            lock.lock();
            if(!hasValue) condition.await();
            System.out.println("★");
            hasValue = false;
            condition.signal();
        }catch (InterruptedException e){
            e.printStackTrace();
        } finally{
            lock.unlock();
        }
    }
}
