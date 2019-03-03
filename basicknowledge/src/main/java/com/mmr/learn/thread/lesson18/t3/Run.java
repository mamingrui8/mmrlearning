package com.mmr.learn.thread.lesson18.t3;

public class Run {
    public static void main(String[] args) {
        MyService service = new MyService();

        ThreadA threadA = new ThreadA(service);
        threadA.start();
    }
}
