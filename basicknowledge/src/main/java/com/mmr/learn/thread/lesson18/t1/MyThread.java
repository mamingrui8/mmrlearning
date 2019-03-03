package com.mmr.learn.thread.lesson18.t1;

public class MyThread extends Thread{
    private MyService service;

    public MyThread(MyService service){
        super();
        this.service = service;
    }

    @Override
    public void run(){
        service.testMethod();
    }
}
