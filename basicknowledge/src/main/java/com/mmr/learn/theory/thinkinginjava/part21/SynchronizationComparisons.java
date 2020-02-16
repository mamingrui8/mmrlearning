package com.mmr.learn.theory.thinkinginjava.part21;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 并发性能测试
 * @author mamr
 * @date 2020/2/15 11:32 下午
 */
public class SynchronizationComparisons {
    static BaseLine baseLine = new BaseLine();
    static SynchronizedTest2 synch = new SynchronizedTest2();
    static LockTest lock = new LockTest();
    static AtomicTest atomic = new AtomicTest();
    static void test() {
        System.out.print("=====================");
        System.out.printf("%-12s : %13d\n", "Cycles", Accumulator.cycles);
        baseLine.timeTest();
        synch.timeTest();
        lock.timeTest();
        // atomic.timeTest();
        Accumulator.report(synch, baseLine);
        Accumulator.report(lock, baseLine);
        //Accumulator.report(atomic, baseLine);
        Accumulator.report(synch, lock);
        //Accumulator.report(synch, atomic);
        //Accumulator.report(lock, atomic);
    }

    public static void main(String[] args) {
        int iterations = 5;
        System.out.println("Warmup");
        baseLine.timeTest();
        // 接下来的测试当中不包含启动线程所花费的时间了
        for (int i = 0; i < iterations; i++) {
            test();
            Accumulator.cycles *=2;
        }
        Accumulator.exec.shutdown();
    }
}

/**
 * 运算
 * 模板方法模式
 */
abstract class Accumulator {
    public static long cycles = 50000L;
    /**
     * 每个测试中修改和读取任务的个数
     */
    private static final int N = 4;
    public static ExecutorService exec = Executors.newFixedThreadPool(2 * N);
    private static CyclicBarrier barrier = new CyclicBarrier(2 * N + 1);
    // 运算的次数
    protected volatile int index = 0;
    // 累计总数
    // 同一种并发手法的不同Modifier和Reader对value形成的并发层面的竞争
    protected volatile long value = 0;
    protected long duration = 0;
    protected String id = "error";
    protected final static int SIZE = 100000;
    protected static int[] preLoaded = new int[SIZE];
    static {
        // 预加载随机产生的数据
        // 在类加载时，jvm会对静态块进行运算，其值预先填充到preLoaded中
        Random random = new Random(47);
        for (int i = 0; i < SIZE; i++) {
            preLoaded[i] = random.nextInt();
        }
    }
    public abstract void accumulate();
    public abstract long read();

    private class Modifier implements Runnable {
        @Override
        public void run() {
            for (long i = 0; i < cycles; i++) {
                accumulate();
            }
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private class Reader implements Runnable {
        private volatile long value;
        @Override
        public void run() {
            for (long i = 0; i < cycles; i++) {
                value = read();
            }
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void timeTest() {
        long start = System.nanoTime();
        // for循环中，barrier.await() 将会执行2*N次
        // 创建了2*N个任务
        for (int i = 0; i < N; i++) {
            exec.execute(new Modifier());
            exec.execute(new Reader());
        }
        // 再执行一次，总共执行了2N + 1次
        // 注意: 使用CyclicBarrier的目的在于保证上面的代码在本轮测试中一定执行完毕

        // CyclicBarrier被初始化计数器的个数为9，如果有读或写操作尚未完成，
        // 那么，即便下方的这个barrier.await()执行完毕，也会把main线程阻塞掉 (因为驱动timeTest()方法的线程是main线程)
        // 此时，整个系统中，处于运行和就绪态的只有尚未完成执行操作的Modifier和Reader了。

        // 从侧面上等待了线程的执行结果 (可以看做是同步的应用)
        try {
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
        duration = System.nanoTime() - start;
        System.out.printf("%-13s: %13d\n", id, duration);
    }

    public static void report(Accumulator acc1, Accumulator acc2) {
        System.out.printf("%-22s: %.2f\n", acc1.id + "/" + acc2.id,
                (double)acc1.duration/(double)acc2.duration);
    }
}

class BaseLine extends Accumulator {
    {id = "BaseLine";}

    @Override
    public void accumulate() {
        if (index >= SIZE-1) {
            index = 0;
        }
        value += preLoaded[index++];
        if (index >= SIZE) {
            index = 0;
        }
    }

    @Override
    public long read() {
        return value;
    }
}

class SynchronizedTest2 extends Accumulator {
    {id = "synchronized";}
    @Override
    public synchronized void accumulate() {
        value += preLoaded[index++];
        if (index >= SIZE) {
            index = 0;
        }
    }

    @Override
    public long read() {
        return value;
    }
}

class LockTest extends Accumulator {
    {id = "Lock";}
    private Lock lock = new ReentrantLock();

    @Override
    public void accumulate() {
        lock.lock();
        try {
            value += preLoaded[index++];
            if (index >= SIZE) {
                index = 0;
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public long read() {
        lock.lock();
        try {
            return value;
        } finally {
            lock.unlock();
        }
    }
}

class AtomicTest extends Accumulator {
    {id = "Atomic";}

    // 本例中，对Atomic对象的使用环境较为复杂，因为后一个Atomic对象依赖了前一个Atomic对象的值，实际上这在jdk文档中时不推荐的
    private AtomicInteger index = new AtomicInteger(0);
    private AtomicLong value = new AtomicLong(0L);

    @Override
    public void accumulate() {
        int i = index.getAndIncrement();
        if (i >= SIZE-1) {
            index.set(0);
        }
        value.getAndAdd(preLoaded[i]);
        if (i >= SIZE-1) {
            index.set(0);
        }
    }

    @Override
    public long read() {
        return value.get();
    }
}