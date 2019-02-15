package com.mmr.learn.thread.lesson6.t6;

public class MyThread1 extends Thread{
    private Task task;
    public MyThread1(Task task){
        super();
        this.task = task;
    }

    @Override
    public void run(){
        super.run();
        CommonUtils.beginTime2 = System.currentTimeMillis();
        task.doLongTimeTask();
        CommonUtils.endTime2 = System.currentTimeMillis();
    }
}
