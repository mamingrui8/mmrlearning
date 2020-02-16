package com.mmr.learn.theory.thinkinginjava.part21.practice30;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 使用BlockingQueue替代PipeReader和PipeWriter 改写PipedIO通信
 */
public class E30_PipedIO {
    public static void main(String[] args) throws InterruptedException{
        LinkedBlockingQueue blockingQueue = new LinkedBlockingQueue();
        Sender sender = new Sender(blockingQueue);
        Receiver receiver = new Receiver(blockingQueue);

        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(sender);
        exec.execute(receiver);
        TimeUnit.SECONDS.sleep(5);
        exec.shutdownNow();
    }
}

class Sender implements Runnable {
    private Random random = new Random(47);
    private LinkedBlockingQueue blockingQueue;
    public Sender(LinkedBlockingQueue blockingQueue) {
        this.blockingQueue = blockingQueue;
    }
    @Override
    public void run() {
        try {
            while(!Thread.interrupted()) {
                for(char c = 'A'; c <= 'z'; c++) {
                    blockingQueue.put(c);
                    TimeUnit.MILLISECONDS.sleep(random.nextInt(500));
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Sender interrupted");
        }
    }
}

class Receiver implements Runnable {
    private LinkedBlockingQueue blockingQueue;
    public Receiver(LinkedBlockingQueue blockingQueue) {
        this.blockingQueue = blockingQueue;
    }
    @Override
    public void run() {
        try {
            while(!Thread.interrupted()) {
                System.out.println("Read: " + (char)blockingQueue.take() + ". ");
            }
        } catch (InterruptedException e) {
            System.out.println("Receiver interrupted");
        }
    }
}