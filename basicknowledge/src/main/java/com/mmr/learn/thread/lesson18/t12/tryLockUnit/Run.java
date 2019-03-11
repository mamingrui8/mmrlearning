package com.mmr.learn.thread.lesson18.t12.tryLockUnit;

public class Run {
    public static void main(String[] args) {
        final MyService service = new MyService();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " 调用waitMethod()的时间是: " + System.currentTimeMillis());
                service.waitMethod();
            }
        };

        Thread threadA = new Thread(runnable);
        threadA.setName("A");
        threadA.start();

        Thread threadB = new Thread(runnable);
        threadB.setName("B");
        threadB.start();
    }
}
