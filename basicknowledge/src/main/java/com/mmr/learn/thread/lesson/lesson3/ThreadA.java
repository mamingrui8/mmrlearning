package com.mmr.learn.thread.lesson.lesson3;

public class ThreadA extends Thread{
    private MyObject object;
    public ThreadA(MyObject object){
        super();
        this.object = object;
    }
    @Override
    public void run(){
        super.run();
        object.methodA();
    }
}
