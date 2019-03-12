package com.mmr.learn.thread.lesson18.t17;

public class ThreadA extends Thread{
    private Service service;

    public ThreadA(Service service){
        super();
        this.service = service;
    }

    @Override
    public void run(){
        service.write();
    }
}
