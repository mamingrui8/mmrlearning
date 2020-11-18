package com.mmr.learn.thread.lesson.lesson6.t6;

import java.util.concurrent.TimeUnit;

public class Run {
    public static void main(String[] args){
        Task task = new Task();
        MyThread1 thread1 = new MyThread1(task);
        thread1.setName("A");
        thread1.start();
        MyThread1 thread2 = new MyThread1(task);
        thread2.setName("B");
        thread2.start();
        try{
            TimeUnit.SECONDS.sleep(2);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        long beginTime = CommonUtils.beginTime1;
        if(CommonUtils.beginTime2 < CommonUtils.beginTime1)
            beginTime = CommonUtils.beginTime2;
        long endTime = CommonUtils.endTime1;
        if(CommonUtils.endTime2 > CommonUtils.endTime1)
            endTime = CommonUtils.endTime2;
        System.out.println("耗时: " + ((endTime - beginTime)/1000));
    }
}