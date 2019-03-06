package com.mmr.learn.thread.lesson18.t9.getHoldCount;

import java.util.concurrent.locks.ReentrantLock;

public class Service {
    private ReentrantLock lock = new ReentrantLock();
    private ReentrantLock lock2 = new ReentrantLock();

    public void serviceMethod1(){
        try {
            lock.lock();
            System.out.println("serviceMethod1 getHoldCount=" + lock.getHoldCount());
            serviceMethod2();
        }finally{
            lock.unlock();
        }
    }

    public void serviceMethod2(){
        try {
            lock2.lock();
            System.out.println("serviceMethod2 getHoldCount=" + lock.getHoldCount());
        }finally{
            lock2.unlock();
        }
    }
}
