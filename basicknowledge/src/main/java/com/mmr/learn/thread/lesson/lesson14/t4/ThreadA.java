package com.mmr.learn.thread.lesson.lesson14.t4;

public class ThreadA extends Thread{
    private Object lock;

    public ThreadA(Object lock){
        super();
        this.lock = lock;
    }

    @Override
    public void run(){
        try {
            synchronized (lock){
                System.out.println("开始   wait time=" + System.currentTimeMillis());
                lock.wait();
                System.out.println("结束   wait time=" + System.currentTimeMillis());
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
