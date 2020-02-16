package com.mmr.learn.theory.thinkinginjava.part21;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Semaphore
 * 多个任务同时访问一个资源
 *
 * @author mamr
 * @date 2020/1/29 8:39 下午
 */
public class SemaphoreDemo {
    final static int SIZE = 25;

    public static void main(String[] args) throws Exception{
        // 初始化对象池，池中有25个Fat对象
        final Pool<Fat> pool = new Pool<>(Fat.class, SIZE);
        ExecutorService exec = Executors.newCachedThreadPool();
        // 创建了25个线程，分别持有25个Fat对象
        for (int i = 0; i < SIZE; i++) {
            exec.execute(new CheckoutTask<>(pool));
        }
        System.out.println("All CheckoutTasks created");
        List<Fat> list = new ArrayList<>();

        // 此时没有任何额外的Fat对象供main线程使用
        // 那么，main线程通过对象池进行对象迁出时，会产生什么后果呢？
        System.out.println("Main checkout...");
        for (int i = 0;i < SIZE; i++) {
            // 若对象池中没有任何对象可被迁出，则checkOut()方法将会被阻塞
            Fat f = pool.checkOut();
            System.out.println(i + ": main() thread checked out");
            f.operation();
            list.add(f);
        }
        // 由于上方代码中，main线程已经把所有的Fat对象都从池中签出了，因此Semaphore将不再允许执行任何的签出操作。
        // 所以blocked中的pool.checkOut()方法一定会被阻塞
        Future<?> blocked = exec.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    // Semaphore会阻止额外的签出操作
                    // 所以当前代码将会被阻塞
                    pool.checkOut();
                } catch (InterruptedException e) {
                    System.out.println("checkOut() Interrupted");
                }
            }
        });
        TimeUnit.SECONDS.sleep(2);
        // Break out of blocked call
        // 为了脱离Future的束缚，调用了Future.cancel()，否则Future会一直等待内部线程执行完毕后的结果
        blocked.cancel(true);
        System.out.println("Checking in objects in " + list);
        for (Fat f : list) {
            pool.checkIn(f);
        }
        for (Fat f : list) {
            // Second checkIn ignored
            pool.checkIn(f);
        }
        exec.shutdown();
    }
}

/**
 * 在pool对象池中管理构造函数创建时间非常长的对象Fat,创建一个新任务，它将签出Fat对象，持有一段时间后再将它们签回，以此来检测Pool类
 */
class CheckoutTask<T> implements Runnable{
    private static int counter = 0;
    private final int id = counter++;
    private Pool<T> pool;
    public CheckoutTask(Pool<T> pool) {
        this.pool = pool;
    }
    @Override
    public void run() {
        try {
            T item = pool.checkOut();
            System.out.println(this + " checked out " + item);
            TimeUnit.SECONDS.sleep(3);
            System.out.println(this + "checking in " + item);
            pool.checkIn(item);
        } catch (InterruptedException e) {
            System.out.println("Acceptable way to terminate");
        }
    }
    @Override
    public String toString() {
        return "CheckoutTask " + id + " ";
    }
}