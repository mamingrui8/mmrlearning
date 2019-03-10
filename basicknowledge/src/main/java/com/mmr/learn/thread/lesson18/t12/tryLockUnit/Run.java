package com.mmr.learn.thread.lesson18.t12.tryLockUnit;

public class Run {
    public static void main(String[] args) {
        final MyService service = new MyService();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread");
            }
        };
    }
}
