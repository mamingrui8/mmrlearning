package com.mmr.learn.theory.thinkinginjava.part21;

import java.util.concurrent.TimeUnit;

/**
 * 通过内部类将线程代码隐藏在类中 匿名内部类且使用Runnable接口
 * @author Charles Wesley
 * @date 2019/12/7 22:37
 */
public class InnerRunnable2 {
    private int countDown = 5;
    private Thread t;
    public InnerRunnable2(String name) {
        t = new Thread(new Runnable(){
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
                //这里用的是Runnable接口，而不是Thread，因此无法使用this.getName()来获取当前线程的名称
                return Thread.currentThread().getName() + ": " + countDown;
            }
        });
    }
}
