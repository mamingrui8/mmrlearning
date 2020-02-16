package com.mmr.learn.theory.thinkinginjava.part21;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 吐司BlockingQueue
 * 现在总共有三个任务:
 * 任务一: 制作吐司
 * 任务二: 给吐司抹黄油
 * 任务三: 给抹过黄油的吐司上涂果酱
 *
 * 通过本例可以学习到如何通过BlockingQueue来协调线程之间的通信
 * 1. 由于队列的阻塞 使得处理过程可以自动的被挂起和恢复
 * 2. 降低了线程之间的耦合度 原先使用wait()和notify()时，A线程通知B线程，B线程通知C线程，依赖关系强，而现在一切交由同步队列来处理
 *    每个类都只和自己的阻塞队列通信。
 */
public class ToastOMatic {
    public static void main(String[] args) throws Exception{
        ToastQueue dryQueue = new ToastQueue();
        ToastQueue butteredQueue = new ToastQueue();
        ToastQueue finishedQueue = new ToastQueue();

        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new Toaster(dryQueue));
        exec.execute(new Butterer(dryQueue, butteredQueue));
        exec.execute(new Jammer(butteredQueue, finishedQueue));
        exec.execute(new Eater(finishedQueue));

        TimeUnit.SECONDS.sleep(5);
        exec.shutdownNow();
    }
}

/**
 * 吐司
 */
class Toast {
    public enum Status { DRY, BUTTERED, JAMMED}
    private Status status = Status.DRY;
    private final int id;
    public Toast(int idn) {
        id = idn;
    }
    public void buffer() { status = Status.BUTTERED; }
    public void jam() {status = Status.JAMMED; }
    public Status getStatus() { return status; }
    public int getId() { return id; }
    public String toString() {
        return "Toast " + id + ": " + status;
    }
}

class ToastQueue extends LinkedBlockingQueue<Toast> {}

/**
 * 制造吐司
 */
class Toaster implements Runnable {
    private ToastQueue toastQueue;
    private int count = 0;
    private Random rand = new Random(47);
    public Toaster(ToastQueue tq) { toastQueue = tq;}
    public void run() {
        try {
            while(!Thread.interrupted()) {
                TimeUnit.MILLISECONDS.sleep(100 + rand.nextInt(500));
                //Make toast
                Toast t = new Toast(count++);
                System.out.println(t);
                //Insert into queue
                toastQueue.put(t);
            }
        }catch (InterruptedException e) {
            System.out.println("Toaster interrupted");
        }
        System.out.println("Toaster off");
    }
}

/**
 * 给吐司涂黄油
 */
class Butterer implements Runnable {
    /**
     * 原味吐司队列
     */
    private ToastQueue dryQueue;
    /**
     * 已经被抹上黄油的吐司队列
     */
    private ToastQueue butteredQueue;

    public Butterer(ToastQueue dry, ToastQueue buttered) {
        this.dryQueue = dry;
        this.butteredQueue = buttered;
    }
    @Override
    public void run() {
        try {
            while(!Thread.interrupted()) {
                // 直到下个吐司制作出来之前，这个方法将一直被阻塞
                Toast t = dryQueue.take();
                t.buffer();
                System.out.println(t);
                butteredQueue.put(t);
            }
        }catch (InterruptedException e) {
            System.out.println("Butterer interrupted");
        }
        System.out.println("Butterer off");
    }
}

/**
 * 给吐司涂果酱 （只有涂抹了黄油的吐司才能涂果酱）
 */
class Jammer implements Runnable {
    /**
     * 涂抹了黄油的吐司队列
     */
    private ToastQueue butteredQueue;
    /**
     * 涂抹了果酱，完全体的吐司队列
     */
    private ToastQueue finishedQueue;

    public Jammer(ToastQueue butteredQueue, ToastQueue finishedQueue) {
        this.butteredQueue = butteredQueue;
        this.finishedQueue = finishedQueue;
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()) {
                // 直到下一个涂了黄油的吐司被制作出来之前，这个方法将一直被阻塞
                Toast t = butteredQueue.take();
                // 涂果酱
                t.jam();
                System.out.println(t);
                // 将完全体的吐司加入到完全体队列中
                finishedQueue.put(t);
            }
        } catch (InterruptedException e) {
            System.out.println("Jammer interrupted");
        }
        System.out.println("Jammer off");
    }
}

/**
 * 吃吐司面包 (消费面包)
 */
class Eater implements Runnable{
    private int counter = 0;

    private ToastQueue finishedQueue;

    public Eater(ToastQueue finishedQueue) {
        this.finishedQueue = finishedQueue;
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()) {
                // 直到下一个完全体吐司被制作出来之前，这个方法将一直被阻塞
                Toast t = finishedQueue.take();
                // 验证吐司是按照顺序制作出炉的
                // 并且每一片面包都涂抹了果酱
                if (t.getId() != counter++ || t.getStatus() != Toast.Status.JAMMED) {
                    System.out.println(">>>> Error: " + t);
                    System.exit(1);
                } else {
                    System.out.println("chomp! " + t);
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Eater interrupted");
        }
        System.out.println("Eater off");
    }
}