package com.mmr.learn.thread.lesson18.t14;

public class MyThreadA extends Thread{
    private Service service;

    public MyThreadA(Service service){
        super();
        this.service = service;
    }

    @Override
    public void run(){
        service.waitMethod();
    }
}
