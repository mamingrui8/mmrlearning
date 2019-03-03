package com.mmr.learn.thread.lesson18.t5;

public class ThreadA extends Thread{
    private MyService service;

    public ThreadA(MyService service){
        this.service = service;
    }

    @Override
    public void run(){
        service.awaitA();
    }
}
