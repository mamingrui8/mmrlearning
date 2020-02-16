package com.mmr.learn.theory.thinkinginjava.part21.practice16;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Charles Wesley
 * @date 2019/12/15 17:57
 */
public class CriticalTest {
    public static void main(String[] args) {
        final DualSynch dualSynch = new DualSynch();
        for(int i = 0; i < 5; i++) {
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
    private Lock lock1 = new ReentrantLock();
    private Lock lock2 = new ReentrantLock();
    private Lock lock3 = new ReentrantLock();

    public void f() {
        boolean capture = lock1.tryLock();
        try {
            if(capture) {
                for (int i = 0; i < 5; i++) {
                    System.out.println("f()");
                    Thread.yield();
                }
            }
        }finally {
            if (capture) {
                lock1.unlock();
            }
        }
    }

    public void g() {
        boolean capture = lock1.tryLock();
        try {
            if(capture) {
                for (int i = 0; i < 5; i++) {
                    System.out.println("g()");
                    Thread.yield();
                }
            }
        }finally {
            if (capture) {
                lock1.unlock();
            }
        }
    }
    public void k() {
        boolean capture = lock1.tryLock();
        try {
            if(capture) {
                for (int i = 0; i < 5; i++) {
                    System.out.println("k()");
                    Thread.yield();
                }
            }
        }finally {
            if (capture) {
                lock1.unlock();
            }
        }
    }
}