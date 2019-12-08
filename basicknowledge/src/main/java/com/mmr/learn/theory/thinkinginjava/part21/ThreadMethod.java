package com.mmr.learn.theory.thinkinginjava.part21;

import java.util.concurrent.TimeUnit;

/**
 * 通过内部类将线程代码隐藏在类中  用一个单独的方法来将线程代码隐藏在类中
 * 当准备好运行线程后，调用runTask()方法即可
 * 比起在类中直接定义线程，这种通过方法调用来运行线程的方式更适合当前类的核心操作不是线程时
 * @author Charles Wesley
 * @date 2019/12/7 22:53
 */
public class ThreadMethod {
    private int countDown = 5;
    private Thread t;
    private String name;
    public ThreadMethod(String name) {
        this.name = name;
    }
    public void runTask() {
        if(t == null) {
            t = new Thread(name){
                @Override
                public void run() {
                    try {
                        while (true) {
                            System.out.println(this);
                            if (--countDown == 0) {
                                return;
                            }
                            TimeUnit.MILLISECONDS.sleep(10);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public String toString() {
                    return getName() + ": " + countDown;
                }
            };
        }
        t.start();
    }
}
