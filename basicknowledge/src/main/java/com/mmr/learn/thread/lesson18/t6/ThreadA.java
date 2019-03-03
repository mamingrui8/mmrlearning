package com.mmr.learn.thread.lesson18.t6;

public class ThreadA extends Thread{
    private MyService myService;

    public ThreadA(MyService myService){
        super();
        this.myService = myService;
    }

    @Override
    public void run(){
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            myService.setValue();
        }
    }
}
