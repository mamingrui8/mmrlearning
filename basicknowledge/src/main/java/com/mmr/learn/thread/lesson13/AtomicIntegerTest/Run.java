package com.mmr.learn.thread.lesson13.AtomicIntegerTest;

public class Run {
    public static void main(String[] args){
        Thread[] threads = new Thread[5];
        for(int i=0; i< 5; i++){
            threads[i] = new AddCountThread();
        }
        for(int i=0; i<5; i++){
            threads[i].start();
        }
    }
}
