package com.mmr.learn.thread.lesson.lesson18.t4;

public class ThreadA extends Thread{
    private MyService service;

    public ThreadA(MyService service){
        this.service = service;
    }

    @Override
    public void run(){
        service.await();
    }
}
