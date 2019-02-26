package com.mmr.learn.thread.lesson14.t7;

public class ThreadA extends Thread{
    private Object lock;

    public ThreadA(Object lock){
        super();
        this.lock = lock;
    }

    @Override
    public void run(){
        Service service = new Service();
        service.testMethod(lock);
    }
}
