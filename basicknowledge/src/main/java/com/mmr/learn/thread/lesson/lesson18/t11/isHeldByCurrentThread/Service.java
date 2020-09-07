package com.mmr.learn.thread.lesson.lesson18.t11.isHeldByCurrentThread;

import java.util.concurrent.locks.ReentrantLock;

public class Service {
    private ReentrantLock lock = new ReentrantLock();

    public void serviceMethod(){
        try {
            System.out.println("当前线程是否保持此锁定呢？ 答: " + lock.isHeldByCurrentThread());
            lock.lock();
            System.out.println("当前线程是否保持此锁定呢？ 答: " + lock.isHeldByCurrentThread());
        }finally{
            lock.unlock();
        }
    }
}
