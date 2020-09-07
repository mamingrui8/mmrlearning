package com.mmr.learn.thread.lesson.lesson18.t8;

public class RunFair {
    public static void main(String[] args) {
        Service service = new Service(false);

        Runnable runnable = new Runnable(){
            @Override
            public void run(){
                System.out.println("☆线程: " + Thread.currentThread().getName() + " 运行了");
                service.serviceMethod();
            }
        };

        Thread[] threads = new Thread[10];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(runnable);
        }

        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
    }
}
