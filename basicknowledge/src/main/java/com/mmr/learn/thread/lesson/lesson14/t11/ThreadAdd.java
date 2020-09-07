package com.mmr.learn.thread.lesson.lesson14.t11;

public class ThreadAdd extends Thread{
    private Add p;

    public ThreadAdd(Add p){
        super();
        this.p = p;
    }

    @Override
    public void run(){
        p.add();
    }
}
