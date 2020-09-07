package com.mmr.learn.thread.lesson.lesson18.t14;

public class MyThreadB extends Thread{
    private Service service;

    public MyThreadB(Service service){
        super();
        this.service = service;
    }

    @Override
    public void run(){
        service.notifyMethod();
    }
}
