package com.mmr.learn.thread.lesson.lesson16.t1;

public class ThreadA extends Thread{
    @Override
    public void run(){
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            String newString = new String();
            Math.random();
        }
    }
}
