package com.mmr.learn.thread.lesson.lesson16.t1;

public class ThreadB extends Thread{
    @Override
    public void run(){
        try {
            ThreadA a = new ThreadA();
            a.start();
            a.join();
            System.out.println("线程B在run end处打印了 ");
        }catch (InterruptedException e){
            System.out.println("线程B在catch处打印了 ");
            e.printStackTrace();
        }
    }
}
