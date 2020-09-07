package com.mmr.learn.thread.lesson.lesson16.t1;

public class Run {
    public static void main(String[] args){
        try {
            ThreadB threadB = new ThreadB();
            threadB.start();
            Thread.sleep(500);
            ThreadC c = new ThreadC(threadB);
            c.start();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
