package com.mmr.learn.thread.lesson18.t7;

public class Run {
    public static void main(String[] args) {
        MyService service = new MyService();
        ThreadA[] threadAS = new ThreadA[10];
        ThreadB[] threadBS = new ThreadB[10];

        for (int i = 0; i < 10; i++) {
            threadAS[i] = new ThreadA(service);
            threadBS[i] = new ThreadB(service);
            threadAS[i].start();
            threadBS[i].start();
        }
    }
}
