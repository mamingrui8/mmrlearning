package com.mmr.learn.thread.lesson16.t2;

public class Run {
    public static void main(String[] args){
        try {
            MyThread myThread = new MyThread();
            myThread.start();
            myThread.join(2000); //单位是毫秒
            System.out.println("end timer=" + System.currentTimeMillis());
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
