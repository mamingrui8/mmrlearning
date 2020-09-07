package com.mmr.learn.thread.lesson.lesson14.t15;

public class Run {
    public static void main(String[] args){
        InsertDBTools insertDBTools = new InsertDBTools();
        for (int i = 0; i < 10; i++) {
            ThreadA threadA = new ThreadA(insertDBTools);
            threadA.start();
            ThreadB threadB = new ThreadB(insertDBTools);
            threadB.start();
        }
    }
}
