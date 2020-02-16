package com.mmr.learn.theory.thinkinginjava.part21;

/**
 * 哲学家问题 筷子
 */
public class Chopstick {
    private boolean taken = false;
    public synchronized void take(int id, String direction) throws InterruptedException{
        while(taken) {
            System.out.println("Philosopher " + id + " waiting " + direction + " chopstick");
            this.wait();
        }
        // 现在由新的哲学家持有这根筷子
        taken = true;
        System.out.println("Philosopher " + id + " grabbed " + direction + " chopstick");
    }
    public synchronized void drop() {
        taken = false;
        this.notifyAll();
    }
}
