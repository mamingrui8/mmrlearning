package com.mmr.learn.theory.thinkinginjava.part21;

/**
 * 两个任务可以同时进入一个对象，只要这个对象上的方法是在不同的锁上同步的即可。
 * 比如下面这个例子中，有两个线程: main线程和Thread线程。分别调用了同一个对象ds的f()和g()。
 * 由于f()方法用到的是DualSynch对象锁，在this上同步，而g()则是在syncObject对象上同步，二者的锁不一样，因此这两种方式是在同时运行的，
 * 任何一个方法都没有对另一个方法的同步而阻塞。
 * @author Charles Wesley
 * @date 2019/12/15 17:34
 */
public class SyncObject {
    public static void main(String[] args) {
        final DualSynch ds = new DualSynch();
        new Thread(){
            @Override
            public void run() {
                ds.f();
            }
        }.start();
        ds.g();
    }
}

class DualSynch {
    private Object syncObject = new Object();

    public synchronized void f() {
        for (int i = 0; i < 5; i++) {
            System.out.println("f()");
            Thread.yield();
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
}