package com.mmr.learn.thread.lesson.lesson18.t12.lockInterruptibly;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class Service {
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void waitMethod(){
        try {
            //lock.lock();
            System.out.println("Thread name: " + Thread.currentThread().getName() + " is interrupted? " + Thread.currentThread().isInterrupted());
            Thread.currentThread().interrupt();
            System.out.println("Thread name: " + Thread.currentThread().getName() + " is interrupted? " + Thread.currentThread().isInterrupted());
            lock.lockInterruptibly();
            System.out.println("lock begin " + Thread.currentThread().getName());
            for (int i = 0; i < Integer.MAX_VALUE/10; i++) {
                String newString = new String();
                Math.random();
            }
            System.out.println("lock end " + Thread.currentThread().getName());
        }catch(Exception e){
            System.out.println("线程" + Thread.currentThread().getName() + "进入catch异常...");
            e.printStackTrace();
        }finally{
            if(lock.isHeldByCurrentThread()){
                lock.unlock();
            }
        }
    }
}
