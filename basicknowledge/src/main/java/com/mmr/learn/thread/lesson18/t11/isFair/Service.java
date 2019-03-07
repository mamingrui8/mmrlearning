package com.mmr.learn.thread.lesson18.t11.isFair;

import java.util.concurrent.locks.ReentrantLock;

public class Service {
    private ReentrantLock lock;

    public Service(boolean flag){
        lock = new ReentrantLock(flag);
    }

    public void serviceMethod(){
        try {
            lock.lock();
            System.out.println("当前锁是否是公平锁？ 答: " + lock.isFair());
        }finally{
            lock.unlock();
        }
    }
}
