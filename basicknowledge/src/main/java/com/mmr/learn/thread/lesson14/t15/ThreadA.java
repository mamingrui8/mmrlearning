package com.mmr.learn.thread.lesson14.t15;

public class ThreadA extends Thread{
    private InsertDBTools insertDBTools;

    public ThreadA(InsertDBTools insertDBTools){
        super();
        this.insertDBTools = insertDBTools;
    }

    @Override
    public void run(){
        insertDBTools.backupA();
    }
}
