package com.mmr.learn.theory.thinkinginjava.part21.practice12;

import com.mmr.learn.theory.thinkinginjava.part21.AtomicityTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @see AtomicityTest 使用synchronzied来修复这个类，现在你能证明它是安全的吗？
 * 答: 能，因为一个线程在改变对象状态时，另一个线程一定处于等待状态。getValue()和evenIncrement()这两个方法都是同步的，因此不可能出现竞争问题。
 * @author Charles Wesley
 * @date 2019/12/14 18:34
 */
public class AtomicityTest2 implements Runnable {
    private volatile int i = 0;

    public synchronized int getValue() {
        return i;
    }

    private synchronized void evenIncrement() {
        i++;
        i++;
    }

    @Override
    public void run() {
        int count = 0;
        while (true) {
            evenIncrement();
            System.out.println(++count);
        }
    }

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        AtomicityTest2 at = new AtomicityTest2();
        exec.execute(at);
        while(true) {
            int val = at.getValue();
            if (val % 2 != 0) {
                System.out.println(val);
                System.exit(0);
            }
        }
    }
}
