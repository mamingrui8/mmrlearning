package com.mmr.learn.theory.thinkinginjava.part21;

import java.time.LocalDateTime;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 简单的测试Synchronized和ReentrantLock执行的效率
 * 单线程执行，不涉及到线程的上下文切换和等待
 *
 * 微基准测试的危险:
 * 1. 不经过真实的环境模拟，通常在隔离的、脱离上下文的环境下对某个特性进行性能测试，这是不准确的。
 *
 * 当多个任务同时访问代码区，产生互斥情况时，才能看到真正的性能差异。
 * 编译器看到synchronized时可能会产生优化，由于本例只有单线程运行，因此有些编译器可能会识别出counter的递增数量是固定的，因此在编译
 * 阶段就对其进行了运算，从而导致运行时synchronized要比lock的效率高。
 *
 * @author mamr
 * @date 2020/2/15 7:08 下午
 */
public class SimpleMicroBenchmark {
    static long test(Incrementable ict) {
        long start = LocalDateTime.now().getNano();
        for (long i = 0; i < 10000000L; i++) {
            ict.increment();
        }
        return LocalDateTime.now().getNano() - start;
    }

    public static void main(String[] args) {
        long syncTime = test(new SynchronizedTest());
        long lockTime = test(new ReentrantLockTest());
        System.out.printf("synchronized: %1$10d\n", syncTime);
        System.out.printf("lock:         %1$10d\n", lockTime);
        System.out.printf("Lock/synchronized = %1$.3f", (double)lockTime/(double)syncTime);
    }
}

abstract class Incrementable {
    protected long counter = 0;
    public abstract void increment();
}

class SynchronizedTest extends Incrementable {
    @Override
    public synchronized void increment() {
        counter++;
    }
}

class ReentrantLockTest extends Incrementable {
    private Lock lock = new ReentrantLock();

    @Override
    public void increment() {
        lock.lock();
        try {
            counter++;
        } finally {
            lock.unlock();
        }
    }
}