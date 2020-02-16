package com.mmr.learn.theory.thinkinginjava.part21.practice21;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 从本案例中学到的内容:
 * 1.一定要用exec.shutdown()而不能使用shutdownNow()。因为后者执行后会把当前线程池中线程驱动的所有任务立即全部停止。
 * 2.只有获得了同一个锁，才能为其它进入wait()的任务使用notifyAll()，否则报错: IllegalMonitorStateException
 */
public class E21_Runnable {
    public static void main(String[] args) throws Exception{
        ExecutorService exec = Executors.newCachedThreadPool();
        E21_Runnable_1 e1 = new E21_Runnable_1();
        E22_Runnable_2 e2 = new E22_Runnable_2(e1);
        exec.execute(e1);
        exec.execute(e2);
        Thread.yield();
        exec.shutdown();
    }
}

class E21_Runnable_1 implements Runnable{
    public E21_Runnable_1() {
        System.out.println("Construct E21_Runnable_1");
    }
    @Override
    public void run() {
        System.out.println("E21_Runnable_1 going to wait");
        synchronized (this) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("E21_Runnable_1 Encountered interrupt Exception");
            }
        }
        System.out.println("E21_Runnable_1 exited wait");
    }
}

class E22_Runnable_2 implements Runnable {
    private E21_Runnable_1 e1;

    public E22_Runnable_2(E21_Runnable_1 e21_runnable_1) {
        this.e1 = e21_runnable_1;
        System.out.println("Construct E22_Runnable_2");
    }

    @Override
    public void run() {
        System.out.println("E22_Runnable_2 pausing 5 seconds");
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            System.out.println("E21_Runnable_2 Encountered interrupt Exception");
        }
        System.out.println("E22_Runnable_2 calling notifyAll");
        synchronized (e1) {
            e1.notifyAll();
        }
        System.out.println("E22_Runnable_2 exited run");
    }
}