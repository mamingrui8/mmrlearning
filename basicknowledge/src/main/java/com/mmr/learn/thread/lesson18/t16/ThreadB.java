package com.mmr.learn.thread.lesson18.t16;

public class ThreadB extends Thread{
    private Service service;

    public ThreadB(Service service){
        this.service = service;
    }

    @Override
    public void run(){
        service.read();
    }
}
