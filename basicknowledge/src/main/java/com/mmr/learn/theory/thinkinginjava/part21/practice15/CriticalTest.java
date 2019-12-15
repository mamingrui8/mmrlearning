package com.mmr.learn.theory.thinkinginjava.part21.practice15;

/**
 * 创建一个类，它具有三个方法，这些方法包含一个临界区，所有对该临界区的同步都在同一个对象上。
 * 创建多个任务来演示这些方法同时只能运行一个。
 *
 * 现在修改这些方法，使得每个方法都在不同的对象上同步，并展示三个方法可以同时运行。
 * @author Charles Wesley
 * @date 2019/12/15 17:47
 */
public class CriticalTest {
    public static void main(String[] args) {
        final DualSynch dualSynch = new DualSynch();
        for (int i = 0; i < 3; i++) {
            new Thread(){
                @Override
                public void run() {
                    dualSynch.f();
                    dualSynch.g();
                    dualSynch.k();
                }
            }.start();
        }
    }
}

class DualSynch {
    private Object syncObject = new Object();
    private Object syncObject2 = new Object();
    private Object syncObject3 = new Object();

    public void f() {
        synchronized(syncObject) {
            for (int i = 0; i < 5; i++) {
                System.out.println("f()");
                Thread.yield();
            }
        }
    }

    public void g() {
        synchronized (syncObject) {
            for (int i = 0; i < 5; i++) {
                System.out.println("g()");
                Thread.yield();
            }
        }
    }
    public void k() {
        synchronized (syncObject) {
            for (int i = 0; i < 5; i++) {
                System.out.println("k()");
                Thread.yield();
            }
        }
    }
}