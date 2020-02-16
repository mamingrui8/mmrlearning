package com.mmr.learn.theory.thinkinginjava.part21;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.io.InputStream;
import java.util.concurrent.Executors;

/**
 * 介绍Executor基本的interrupt()的使用方法
 * Executor停止线程主要涉及到以下两种场景:
 * 1. 停止由Executor创建出的全部线程
 * Executor.shutdownNow() 线程池的线程调度器会对池中所有的线程发送一条interrupt()指令
 * 2. 停止某个指定的任务
 * 通过submit()而不是execute()来启动任务。使用submit()返回的cancel()方法来中断指定任务
 *
 * 不难发现，IoBlocked和SynchronizedBlocked创建出的任务在被中断后，直接放弃了未执行完毕的任务内容，并且不再继续运行。
 * 而sleep()则不同，任务被中断后，仍然能继续运行未执行完毕的任务。
 * =====> 推断:
 *        1. SleepingBlocked是可中断的阻塞示例，而IoBlocked和SynchronizedBlocked则是不可中断的阻塞示例。
 *        2. 我们能中断对sleep()的调用(或者任何声明抛出InterruptedException的操作)，但我们不能中断正在试图获取synchronized锁或是
 *           试图执行I/O操作的线程。这点非常恶心，一旦多线程程序中含有I/O操作，那么整个程序就存在被I/O操作锁死的可能(因为别的任务如果想执行I/O操作，则)。
 *
 * @author mamr
 */
public class Interrupting{
    public static ExecutorService exec = Executors.newCachedThreadPool();

    static void test(Runnable runnable) throws InterruptedException{
        Future<?> f = exec.submit(runnable);
        //停掉main()线程，让出cpu时间片，使runnable运行
        TimeUnit.MILLISECONDS.sleep(100);

        System.out.println("interrupting " + runnable.getClass().getName());
        //中断runnable任务的运行 (100%能中断吗？是立即中断吗？)
        f.cancel(true);
        System.out.println("Interrupt sent to " + runnable.getClass().getName());
    }

    public static void main(String[] args) throws Exception{
        test(new SleepingBlocked());
        test(new IoBlocked(System.in));
        test(new SynchronizedBlocked());
        System.out.println("Aborting with System.exit(0)");
        System.exit(0);
    }
}

/**
 * 阻塞态 原因: sleep()
 */
class SleepingBlocked implements Runnable{
    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(100);
        }catch (InterruptedException e) {
            System.out.println("interruptedException");
        }
        System.out.println("Exiting SleepingBlocked.run()");
    }
}

/**
 * 阻塞态 原因: 等待IO完成
 */
class IoBlocked implements Runnable {
    private InputStream in;

    public IoBlocked(InputStream in) {this.in = in;}

    @Override
    public void run() {
        try {
            System.out.println("Waiting for read():");
            in.read();
        }catch (IOException e) {
            if(Thread.currentThread().isInterrupted()) {
                System.out.println("Interrupted from blocked I/O");
            }else {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Existing IOBlocked.run()");
    }
}

/**
 * 阻塞态 原因: 同步锁被其它任务获取了
 */
class SynchronizedBlocked implements Runnable {
    public synchronized void f() {
        //永远不会释放对象锁
        while(true) {
            Thread.yield();
        }
    }

    public SynchronizedBlocked() {
        //该线程将对象锁拿走了，由于f()不会返回，因此对象锁永远不会被释放！
        //注意: 这个新线程必须有别于驱动run()的线程，因为一个线程可以获取多次某个对象锁。
        new Thread(){
            @Override
            public void run() {
                f();
            }
        }.start();
    }

    @Override
    public void run() {
        System.out.println("Trying to call f()");
        f();
        System.out.println("Existing SynchronizedBlocked.run()");
    }
}