package com.mmr.learn.thread.lesson.lesson14.t15;

public class ThreadB extends Thread{
    private InsertDBTools insertDBTools;

    public ThreadB(InsertDBTools insertDBTools){
        super();
        this.insertDBTools = insertDBTools;
    }

    @Override
    public void run(){
        insertDBTools.backupB();
    }
}
