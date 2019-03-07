package com.mmr.learn.thread.lesson18.t11.isLocked;

import java.util.concurrent.locks.ReentrantLock;

public class Service {
    private ReentrantLock lock = new ReentrantLock();

    public void serviceMethod(){
        try {
            lock.lock();
            System.out.println("当前锁是否被线程持有了？答: " + lock.isLocked());
        }finally{
            lock.unlock();
            System.out.println("释放锁...");
            System.out.println("当前锁是否被线程持有了？答: " + lock.isLocked());
        }
    }
}
