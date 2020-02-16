package com.mmr.learn.theory.thinkinginjava.part21;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 使用AtomicInteger原子类来重写AtomicityTest类
 *
 * 可以发现，本例中使用AtomicInteger原子类型完全替代了schronized关键字
 * @author Charles Wesley
 * @date 2019/12/14 21:00
 */
public class AtomicIntegerTest implements Runnable{
    private AtomicInteger i = new AtomicInteger(0);
    public int getValue() {
        return i.get();
    }
    private void evenIncrement() {
        i.addAndGet(2);
    }

    @Override
    public void run() {
        while(true) {
            evenIncrement();
        }
    }

    public static void main(String[] args) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Aborting");
                System.exit(0);
            }
            //仅让程序运行5秒，5秒后会推出程序，杀死java进程
        }, 5000);
        ExecutorService exec = Executors.newCachedThreadPool();
        AtomicIntegerTest ait = new AtomicIntegerTest();
        exec.execute(ait);
        while(true) {
            int val = ait.getValue();
            if(val % 2 != 0) {
                System.out.println("奇数: " + val);
                System.exit(0);
            }
        }
    }
}
