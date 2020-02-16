package com.mmr.learn.theory.thinkinginjava.part21;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * 哲学家 死锁案例
 *
 * 问题:
 * 1. 为什么会产生死锁？
 * 2. 为什么ponder的值越大，死锁出现的概率越低？
 *    答: 若ponder值非常小，则，由于大量的哲学家涌入这个阶段，导致循环等待的发生。
 *
 *    注意，产生死锁的可能性大小真正的影响因素在于 请求共享资源的可能性
 *
 *    同一时刻，大量的哲学家打算用餐，
 */
public class DeadlockingDiningPhilosopher {
    public static void main(String[] args) throws InterruptedException, IOException {
        int ponder = 3;
        if(args.length > 0) {
            ponder = Integer.parseInt(args[0]);
        }
        int size = 5;
        if (args.length > 1) {
            size = Integer.parseInt(args[1]);
        }

        ExecutorService exec = Executors.newCachedThreadPool();
        Chopstick[] sticks = new Chopstick[size];

        for(int i = 0; i < size; i++) {
            sticks[i] = new Chopstick();
        }

        for (int i = 0; i < size; i++) {
            exec.execute(new Philosopher(sticks[i], sticks[(i+1) % size], i, ponder));
        }

        if (args.length == 3 && args[2].equals("timeout")) {
            TimeUnit.SECONDS.sleep(5);
        } else {
            System.out.println("Press 'Enter' to quit");
            System.in.read();
        }
        exec.shutdownNow();
    }
}
