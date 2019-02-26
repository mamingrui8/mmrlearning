package com.mmr.learn.thread.lesson14.t11;

import java.util.concurrent.TimeUnit;

public class Run {
    public static void main(String[] args){
        try {
            String lock = new String("");
            Add add = new Add(lock);
            Subtract subtract = new Subtract(lock);

            ThreadSubtract subtract1Thread = new ThreadSubtract(subtract);
            subtract1Thread.setName("subtract1Thread");
            subtract1Thread.start();

            ThreadSubtract subtract2Thread = new ThreadSubtract(subtract);
            subtract2Thread.setName("subtract2Thread");
            subtract2Thread.start();

            TimeUnit.SECONDS.sleep(1);
            ThreadAdd addThread = new ThreadAdd(add);
            addThread.setName("addThread");
            addThread.start();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
