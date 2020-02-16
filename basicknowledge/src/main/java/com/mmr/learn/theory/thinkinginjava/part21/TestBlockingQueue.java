package com.mmr.learn.theory.thinkinginjava.part21;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * 使用BlockingQueue轻松解决任务互操作的问题
 *
 * notifyAll()和wait()方法的劣势在于，每次交互都需要进行握手(通知另一个任务)
 * 而blockingQueue在解决任务协作问题时，同一时刻只允许一个任务插入或移除元素。
 */
public class TestBlockingQueue {
    static void getKey() {
        try {
            new BufferedReader(new InputStreamReader(System.in)).readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    static void getKey(String message) {
        System.out.println(message);
        getKey();
    }
    static void test(String message, BlockingQueue<LifeOff> queue) {
        System.out.println(message);
        LifeOffRunner runner = new LifeOffRunner(queue);
        Thread thread = new Thread(runner);
        thread.start();

        for(int i = 0; i < 5; i++) {
            runner.add(new LifeOff(5));
        }
        getKey("Press 'Enter' (" + message + ")");
        thread.interrupt();
        System.out.println("Finished " + message + " test");
    }
    public static void main(String[] args) {
        //Ultimate size
        test("LinkedBlockingQueue", new LinkedBlockingQueue<>());

        //Fixed size
        test("ArrayBlockingQueue", new ArrayBlockingQueue<>(3));

        //Size of 1
        test("SynchronousQueue", new SynchronousQueue<>());
    }
}

class LifeOffRunner implements Runnable {
    private BlockingQueue<LifeOff> rockets;
    public LifeOffRunner(BlockingQueue<LifeOff> queue) {
        this.rockets = queue;
    }
    public void add(LifeOff lo) {
        try {
            //TODO put()和add()有什么区别呢？
            rockets.put(lo);
        }catch (InterruptedException e) {
            System.out.println("Interrupted during put()");
        }
    }
    @Override
    public void run() {
        try {
            while(!Thread.interrupted()) {
                LifeOff rocket = rockets.take();
                //注意: 这里没有重新开启一个新的线程来驱动任务，而是直接调用了任务的run()方法
                rocket.run();
            }
        } catch (InterruptedException e) {
            System.out.println("Waking from take()");
        }
        System.out.println("Exiting LiftOffRunner");
    }
}