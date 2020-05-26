package com.mmr.learn.theory.thinkinginjava.part21;

import com.sun.tools.internal.ws.wsdl.document.soap.SOAPUse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReadWriteLock基本用法
 * @author mamr
 * @date 2020/2/29 7:00 下午
 */
public class ReaderWriteList<T> {
    /**
     * ReadWriteLock针对以下场景进行了优化:
     * 1. 多个任务需要读取这个数据结构
     * 2. 相对较少的任务需要写入到这个数据结构
     * 3. 被读取的频率比被写入的频率高得多
     * 4. 在多处理器的机器上运行
     *
     * 若写锁已经被其它任务持有，那么任何读取者都无法访问，直到写锁被释放。
     */

    private ArrayList<T> lockedList;
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
    public ReaderWriteList(int size, T initialValue) {
        lockedList = new ArrayList<T>(Collections.nCopies(size, initialValue));
    }
    public T set(int index, T element) {
        // 获得写锁
        Lock wlock = lock.writeLock();
        wlock.lock();
        try {
            return lockedList.set(index, element);
        } finally {
            wlock.unlock();
        }
    }
    public T get(int index) {
        // 获得读锁
        Lock rlock = lock.readLock();
        rlock.lock();
        try {
            // 可能会有多个读取者同时读取"读锁"
            if (lock.getReadLockCount() > 1) {
                System.out.println(lock.getReadLockCount());
            }
            return lockedList.get(index);
        } finally {
            rlock.unlock();
        }
    }

    public static void main(String[] args) throws Exception{
        // 创建30个读取者，1个写入者   写入者的数量远小于读取者
        new ReaderWriterListTest(30, 1);
    }
}

class ReaderWriterListTest {
    ExecutorService exec = Executors.newCachedThreadPool();
    private final static int SIZE = 100;
    private static Random rand = new Random(47);
    private ReaderWriteList<Integer> list = new ReaderWriteList<>(SIZE, 0);
    private class Writer implements Runnable {
        @Override
        public void run() {
            try {
                for (int i = 0; i < 20; i++) {
                    list.set(i, rand.nextInt());
                    TimeUnit.MILLISECONDS.sleep(100);
                }
            } catch (InterruptedException E) {
                System.out.println("Acceptable way to exit");
            }
            System.out.println("Write finished, shutting down");
            exec.shutdownNow();
        }
    }
    private class Reader implements Runnable {
        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    for (int i = 0; i < SIZE; i++) {
                        list.get(i);
                        TimeUnit.MILLISECONDS.sleep(1);
                    }
                }
            } catch (InterruptedException e) {
                System.out.println("Acceptable way to exit");
            }
        }
    }
    public ReaderWriterListTest(int readers, int writers) {
        for (int i = 0; i < readers; i++) {
            exec.execute(new Reader());
        }
        for (int i = 0; i < writers; i++) {
            exec.execute(new Writer());
        }
    }
}
