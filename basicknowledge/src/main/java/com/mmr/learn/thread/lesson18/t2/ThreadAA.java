package com.mmr.learn.thread.lesson18.t2;

public class ThreadAA extends Thread{
    private MyService service;

    public ThreadAA(MyService service){
        super();
        this.service = service;
    }

    @Override
    public void run(){
        service.methodA();
    }
}
