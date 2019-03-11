package com.mmr.learn.thread.lesson18.t13.basic;

public class MyThread extends Thread{
    private Service service;

    public MyThread(Service service){
        this.service = service;
    }

    @Override
    public void run(){
        service.testMethod();
    }
}
