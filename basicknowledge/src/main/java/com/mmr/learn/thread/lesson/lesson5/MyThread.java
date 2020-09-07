package com.mmr.learn.thread.lesson.lesson5;

public class MyThread extends Thread{
    @Override
    public void run(){
        Service service = new Service();
        service.service1();
    }
}
