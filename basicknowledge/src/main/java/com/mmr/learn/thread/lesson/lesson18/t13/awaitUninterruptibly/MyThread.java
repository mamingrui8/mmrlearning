package com.mmr.learn.thread.lesson.lesson18.t13.awaitUninterruptibly;

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
