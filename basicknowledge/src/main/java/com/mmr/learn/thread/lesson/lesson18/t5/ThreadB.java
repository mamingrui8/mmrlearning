package com.mmr.learn.thread.lesson.lesson18.t5;

public class ThreadB extends Thread{
    private MyService myService;

    public ThreadB(MyService myService){
        this.myService = myService;
    }

    @Override
    public void run(){
        myService.awaitB();
    }
}
