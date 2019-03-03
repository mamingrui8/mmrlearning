package com.mmr.learn.thread.lesson18.t6;

public class Run {
    public static void main(String[] args) {
        MyService myService = new MyService();

        ThreadA threadA = new ThreadA(myService);
        threadA.setName("A");
        threadA.start();

        ThreadB threadB = new ThreadB(myService);
        threadB.setName("B");
        threadB.start();
    }
}
