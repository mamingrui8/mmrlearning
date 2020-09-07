package com.mmr.learn.thread.lesson.lesson18.t10.hasQueuedThread;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Service {
    public ReentrantLock lock = new ReentrantLock();
    public Condition condition = lock.newCondition();

    public void waitMethod(){
        try {
            lock.lock();
            TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }
}
