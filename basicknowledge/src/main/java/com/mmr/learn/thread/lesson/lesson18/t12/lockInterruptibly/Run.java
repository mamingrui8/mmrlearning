package com.mmr.learn.thread.lesson.lesson18.t12.lockInterruptibly;

import java.util.concurrent.TimeUnit;

@SuppressWarnings("Duplicates")
public class Run {
    /**
     * A线程未被中断，因此可以顺序获取锁定。B线程在start()后立刻执行了interrupt()，
     * threadB在恰当的时候停了下来，针对已经被中断的线程执行lock.lockInterruptibly()会报错: java.lang.InterruptedException
     * 但有意思的是，Lock.lock()并不会受到线程中断的影响
     */
    public static void main(String[] args) throws InterruptedException{
        final Service service = new Service();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                service.waitMethod();
            }
        };
        Thread threadA = new Thread(runnable);
        threadA.setName("A");
        threadA.start();

        TimeUnit.SECONDS.sleep(1);

        Thread threadB = new Thread(runnable);
        threadB.setName("B");
        threadB.start();
        //threadB.interrupt(); //打标记
        System.out.println("main end!");
    }

}
