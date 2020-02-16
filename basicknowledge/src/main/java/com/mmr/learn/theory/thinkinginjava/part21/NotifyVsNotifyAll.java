package com.mmr.learn.theory.thinkinginjava.part21;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class NotifyVsNotifyAll {
    public static void main(String[] args) throws Exception{
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            exec.execute(new Task());
        }
        exec.execute(new Task2());
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            boolean prod = true;
            @Override
            public void run() {
                if (prod) {
                    System.out.println("\nnotify()");
                    Task.blocker.prod();
                    prod = false;
                } else {
                    System.out.println("\nnotifyAll()");
                    Task.blocker.prodAll();
                    prod = true;
                }
            }
        }, 400, 400);
        //Run for a while...
        TimeUnit.SECONDS.sleep(5);
        timer.cancel();
        System.out.println("\nTimer canceled");

        TimeUnit.MILLISECONDS.sleep(500);
        System.out.println("Task2.blocker.prodAll");
        Task2.blocker.prodAll();
        TimeUnit.MILLISECONDS.sleep(500);
        //Interrupt all tasks
        exec.shutdownNow();
    }
}

class Blocker {
    synchronized void waitingCall() {
        try {
            //检查interrupt 离开循环
            while(!Thread.interrupted()) {
                wait();
                System.out.println(Thread.currentThread() + " ");
            }
        }catch (InterruptedException e) {
            System.out.println("Blocker interrupt");
        }
    }
    synchronized void prod() {
        notify();
    }
    synchronized void prodAll() {
        notifyAll();
    }
}

class Task implements Runnable {
    //Task的阻塞对象
    static Blocker blocker = new Blocker();
    @Override
    public void run() {
        blocker.waitingCall();
    }
}

class Task2 implements Runnable {
    //Task2的阻塞对象
    static Blocker blocker = new Blocker();
    @Override
    public void run() {
        blocker.waitingCall();
    }
}
