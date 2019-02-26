package com.mmr.learn.thread.lesson14.t1;

import java.util.concurrent.TimeUnit;

public class Run {
    public static void main(String[] args) throws InterruptedException{
        MyList myList = new MyList();
        ThreadA a = new ThreadA(myList);
        a.setName("A");
        a.start();
        ThreadB b = new ThreadB(myList);
        b.setName("B");
        b.start();
        TimeUnit.SECONDS.sleep(10);
        System.out.println("a isInterrupted -> " + a.isInterrupted());
        System.out.println("b isInterrupted -> " + b.isInterrupted());
    }
}
