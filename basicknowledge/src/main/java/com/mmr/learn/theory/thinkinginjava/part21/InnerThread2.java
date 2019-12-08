package com.mmr.learn.theory.thinkinginjava.part21;

import java.util.concurrent.TimeUnit;

/**
 * 通过内部类将线程代码隐藏在类中 - 使用的是匿名内部类
 * InnerThread2内的其它方法若想访问t，那么它们可以通过调用Thread接口的方法来实现，而不用关心t到底是怎么生成的。
 * @author Charles Wesley
 * @date 2019/12/7 22:21
 */
public class InnerThread2 {
    private int countDown = 5;
    private Thread t;
    public InnerThread2(String name) {
        t = new Thread(name) {
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

            @Override
            public String toString() {
                return this.getName() + ": " + countDown;
            }
        };
        t.start();
    }

    public void c() {
        t.setPriority(1);
    }
}
