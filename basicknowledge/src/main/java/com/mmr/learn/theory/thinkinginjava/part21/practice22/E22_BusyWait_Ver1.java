package com.mmr.learn.theory.thinkinginjava.part21.practice22;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;

public class E22_BusyWait_Ver1 {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        E22_Label e22_label = new E22_Label();
        E22_BusyWait_1 e22_busyWait_1 = new E22_BusyWait_1(e22_label);
        E22_BusyWait_2 e22_busyWait_2 = new E22_BusyWait_2(e22_label);
        exec.execute(e22_busyWait_1);
        exec.execute(e22_busyWait_2);
        Thread.yield();
        exec.shutdown();
    }
}

class E22_Label {
    private boolean flag = false;

    public synchronized void trueFlag() {
        System.out.println("flag = true");
        flag = true;
    }

    public synchronized void waitFlag() {
        System.out.println("Enter waitFlag " + LocalDateTime.now());
        while(true) {
            if(flag == true) {
                flag = false;
                break;
            }
        }
        System.out.println("Exit waitFlag " + LocalDateTime.now());
    }
}

class E22_BusyWait_1 implements Runnable{
    private E22_Label e22_label;
    public E22_BusyWait_1(E22_Label e22_label) {
        this.e22_label = e22_label;
        System.out.println("Enter Construct E22_BusyWait_1");
    }

    @Override
    public void run() {
        System.out.println("E22_BusyWait_1 going to wait 3 seconds");
        try {
            TimeUnit.SECONDS.sleep(3);
        }catch (InterruptedException e) {
            System.out.println("E22_BusyWait_1 encountered interrupt");
        }

        System.out.println("past 3 seconds");
        synchronized (e22_label) {
            e22_label.trueFlag();
        }
    }
}

class E22_BusyWait_2 implements Runnable{
    private E22_Label e22_label;
    public E22_BusyWait_2(E22_Label e22_label) {
        this.e22_label = e22_label;
        System.out.println("Enter Construct E22_BusyWait_2");
    }
    @Override
    public void run() {
        synchronized (e22_label) {
            e22_label.waitFlag();
        }
    }
}

