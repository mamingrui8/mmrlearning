package com.mmr.learn.thread.lesson.lesson18.t14;

import java.util.Calendar;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Service {
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void waitMethod(){
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.SECOND, 10);

            if(lock.tryLock()){
                System.out.println("wait begin timer = " + System.currentTimeMillis());
                condition.awaitUntil(calendar.getTime());
                System.out.println("wait end timer = " + System.currentTimeMillis());
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally{
            if(lock.isHeldByCurrentThread())
                lock.unlock();
        }
    }

    public void notifyMethod(){
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.SECOND, 10);

            if(lock.tryLock()){
                System.out.println("notify begin timer = " + System.currentTimeMillis());
                condition.signalAll();
                System.out.println("notify end timer = " + System.currentTimeMillis());
            }
        }finally{
            if(lock.isHeldByCurrentThread()){
                lock.unlock();
            }
        }
    }
}
