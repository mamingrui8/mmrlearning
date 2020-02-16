package com.mmr.learn.theory.thinkinginjava.part21;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * reentrantLock介绍。
 * 能做到synchronized做不的事情: 中断那些在synchronized中看似不可被打断的线程。
 */
public class Interrupting2 {
    public static void main(String[] args) throws Exception{
        Thread t = new Thread(new Blocked2());
        t.setName("Blocked2");
        t.start();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("Issuing t.interrupt()");
        t.interrupt();
    }
}

class BlockedMutex {
    private Lock lock = new ReentrantLock();

    public BlockedMutex () {
        //构造函数中直接获取这把对象锁，并且不释放 (如果没有获取到锁，则一直处于阻塞状态！)
        //注意: reentrantLock与Synchronized不同的是，方法执行完毕后，lock锁并不会被自动释放
        lock.lock();
        f();
    }

    public void f() {
        try {
            System.out.println("Thread Name: " + Thread.currentThread().getName());
            lock.lockInterruptibly();
        } catch (InterruptedException e) {
            System.out.println("Interrupted from lock acquisition in f()");
        }
    }
}

class Blocked2 implements Runnable{
    BlockedMutex blocked = new BlockedMutex();
    @Override
    public void run() {
        System.out.println("Waiting for f() in BlockedMutex");
        blocked.f();
        System.out.println("Broken out of blocked call");
    }
}

/*
1. 执行BlockedMutex的构造函数，由main线程执行lock.lock()获取到对象锁，而lock.lockInterruptibly()由于已经获取到了锁，
   因此立即正常返回(注意，此处并没有释放锁，锁仍然在main线程手上！)。
2. 线程t趁着main线程休眠的时候，进入run() 输出: Waiting for f() in BlockedMutex，接着赶紧执行f()，哪知道锁在main线程手里，
   于是线程t进入阻塞状态，等待main线程释放对象锁。
3. main线程执行Issuing t.interrupt()，并执行了t.interrupt()。这样一来，原本处于阻塞状态的线程t被中断，抛出InterruptedException异常
4. 最后，线程t执行完自己run()的最后一条语句 Broken out of blocked call。程序结束

Thread Name: main thread
Waiting for f() in BlockedMutex
Thread Name: Blocked2
Issuing t.interrupt()
Interrupted from lock acquisition in f()
Broken out of blocked call
 */
