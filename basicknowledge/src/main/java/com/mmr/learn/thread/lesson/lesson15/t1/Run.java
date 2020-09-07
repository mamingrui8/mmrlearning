package com.mmr.learn.thread.lesson.lesson15.t1;

public class Run {
    public static void main(String[] args){
        MyThread thread = new MyThread();
        thread.start();
        System.out.println("在执行完main线程之前，我想知道MyThread中secondValue的值到底是多少! ");
        System.out.println("main线程执行完毕");
    }
}
