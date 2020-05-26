package com.mmr.learn.theory.thinkinginjava.part21;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * CountDownLatch的示例
 * TaskPortion属于前置任务，每次运行时都会等待一段时间用于模拟任务处理。当前置任务全部执行完毕后，等待任务WaitingTask才开始运行。
 * 所有的任务都使用了同一个在main()中定义的CountDownLatch对象。
 * 主要有以下三个方法:
 * 1. CountDownLatch countDown() 使计数器减一
 * 2. CountDownLatch latch.await() 使当前线程阻塞，当且仅当计数器的值为0时，线程恢复成可执行态
 * 3. new CountDownLatch(int SIZE)  初始化一个CountDownLatch对象，计数器的最大值为SIZE
 */
public class CountDownLatchDemo {
    static final int SIZE = 5;

    public static void main(String[] args) throws Exception{
        ExecutorService exec = Executors.newCachedThreadPool();
        CountDownLatch latch = new CountDownLatch(SIZE);
        // 10个等待任务
        for (int i = 0; i < 10; i++) {
            exec.execute(new WaitingTask(latch));
        }
        // 100个前置任务
        for (int i = 0; i < SIZE; i++) {
            exec.execute(new TaskPortion(latch));
        }
        System.out.println("Launched all tasks");
        //Quit when all tasks complete
        exec.shutdown();
    }
}

class TaskPortion implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private static Random rand = new Random(47);
    private final CountDownLatch latch;
    TaskPortion(CountDownLatch latch) {
        this.latch = latch;
    }
    @Override
    public void run() {
        try {
            doWork();
            latch.countDown();
        } catch (InterruptedException e) {
            // Acceptable way to exit

        }
    }
    public void doWork() throws InterruptedException{
        TimeUnit.MILLISECONDS.sleep(rand.nextInt(2000));
        System.out.println(this + " completed");
    }
    @Override
    public String toString() {
        return String.format("%1$-3d ", id);
    }
}

class WaitingTask implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private final CountDownLatch latch;
    WaitingTask(CountDownLatch latch) {
        this.latch = latch;
    }
    @Override
    public void run() {
        try {
            latch.await();
            System.out.println("Latch barrier passed for " + this);
        } catch (InterruptedException e) {
            System.out.println(this + " interrupted");
        }
    }
    @Override
    public String toString() {
        return String.format("WaitingTask %1$-3d", id);
    }
}
