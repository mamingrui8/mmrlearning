package com.mmr.learn.thread.lesson18.t2;

public class ThreadB extends Thread{
    private MyService service;

    public ThreadB(MyService service){
        super();
        this.service = service;
    }

    @Override
    public void run(){
        service.methodB();
    }
}
