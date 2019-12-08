package com.mmr.learn.theory.thinkinginjava.part21;

import java.util.concurrent.TimeUnit;

/**
 * 通过内部类将线程代码隐藏在类中 - 使用的是有名字且继承了Thread的内部类Inner
 * @author Charles Wesley
 * @date 2019/12/4 23:43
 */
public class InnerThread1 {
    private int countDown = 5;
    private Inner inner;

    public InnerThread1(String name) {
        this.inner = new Inner(name);
    }

    /**
     * 内部类 同时也是一个线程类
     */
    private class Inner extends Thread{
        Inner(String name) {
            super(name);
            this.start();
        }
        public void run() {
            try {
                while(true) {
                    System.out.println(this);
                    if(--countDown == 0) {
                        return;
                    }
                    TimeUnit.MILLISECONDS.sleep(10);
                }
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public String toString() {
            return this.getName() + ": " + countDown;
        }
    }
}
