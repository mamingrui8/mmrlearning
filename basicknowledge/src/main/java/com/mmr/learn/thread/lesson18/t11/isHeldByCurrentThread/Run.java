package com.mmr.learn.thread.lesson18.t11.isHeldByCurrentThread;

@SuppressWarnings("Duplicates")
public class Run {
    public static void main(String[] args) {
        final Service service = new Service();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                service.serviceMethod();
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }
}
