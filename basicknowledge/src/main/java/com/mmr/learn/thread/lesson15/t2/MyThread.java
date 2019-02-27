package com.mmr.learn.thread.lesson15.t2;

public class MyThread extends Thread{
    @Override
    public void run(){
        try {
            int secondValue = (int) (Math.random()) * 10000;
            System.out.println(secondValue);
            Thread.sleep(secondValue);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}