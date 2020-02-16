package com.mmr.learn.theory.thinkinginjava.part21;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 经过试验发现:
 * 前一个任务被I/O操作阻塞后，不会释放对象锁，直接导致依赖同一个对象的其它任务全部被阻塞！
 */
public class ThreadIOLock {
    private static ExecutorService exec = Executors.newCachedThreadPool();

    public static void main(String[] args) throws InterruptedException {
        ThreadInnerIOLock t1 = new ThreadInnerIOLock(System.in);
        exec.execute(t1);
        exec.execute(t1);
        TimeUnit.MILLISECONDS.sleep(300);

        ThreadInnerIOLock t2 = new ThreadInnerIOLock(System.in);
        exec.execute(t2);
        exec.shutdownNow();
    }
}

class ThreadInnerIOLock implements Runnable{
    private InputStream in;

    public ThreadInnerIOLock (InputStream in) {
        this.in = in;
    }

    public synchronized void f() {
        System.out.println(Thread.currentThread().getName() + " : f()");
        try {
            in.read();
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }

    @Override
    public void run() {
        f();
        System.out.println(Thread.currentThread().getName() + " exit run()");
    }
}
