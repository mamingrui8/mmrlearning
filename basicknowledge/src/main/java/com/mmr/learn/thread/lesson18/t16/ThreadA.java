package com.mmr.learn.thread.lesson18.t16;

public class ThreadA extends Thread{
    private Service service;

    public ThreadA(Service service){
        super();
        this.service = service;
    }

    @Override
    public void run(){
        service.read();
    }
}