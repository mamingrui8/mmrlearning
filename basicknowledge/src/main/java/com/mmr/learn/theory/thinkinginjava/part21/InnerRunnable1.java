package com.mmr.learn.theory.thinkinginjava.part21;

import java.util.concurrent.TimeUnit;

/**
 * 通过内部类将线程的代码隐藏在类中 - 使用的是有名字且实现了Runnable接口的内部类
 * @author Charles Wesley
 * @date 2019/12/7 22:32
 */
public class InnerRunnable1 {
    private int countDown = 5;
    private Inner inner;
    private class Inner implements Runnable {
        Thread t;
        Inner(String name) {
            t = new Thread(this, name);
            t.start();
        }
        @Override
        public String toString() {
            return t.getName() + ": " + countDown;
        }

        @Override
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
    }
}
