package com.mmr.learn.thread.lesson.lesson14.t11;

public class ThreadSubtract extends Thread{
    private Subtract r;

    public ThreadSubtract(Subtract r){
        super();
        this.r = r;
    }

    @Override
    public void run(){
        r.subtract();
    }
}
