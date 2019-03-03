package com.mmr.learn.thread.lesson18.t1;

public class Run {
    public static void main(String[] args) {
        MyService myService = new MyService();
        for (int i = 0; i < 5; i++) {
            MyThread thread = new MyThread(myService);
            thread.setName(i + "");
            thread.start();
        }
    }
}
