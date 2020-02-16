package com.mmr.learn.theory.thinkinginjava.part21;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 哲学家问题 哲学家
 */
public class Philosopher implements Runnable{
    private Chopstick left;
    private Chopstick right;
    private final int id;
    private final int ponderFactor;
    private Random rand = new Random(47);

    private void pause() throws InterruptedException{
        if(ponderFactor == 0) {
            return;
        }
        TimeUnit.MILLISECONDS.sleep(rand.nextInt(ponderFactor * 250));
    }

    public Philosopher(Chopstick left, Chopstick right, int ident, int ponder) {
        this.left = left;
        this.right = right;
        this.id = ident;
        this.ponderFactor = ponder;
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()) {
                // 为什么加上的等待时间越长，产生死锁的可能性越小？
                pause();
                //System.out.println(this + " beginning eating " + LocalDateTime.now().getNano());
                // 哲学家开始变饿
                System.out.println(this + " " + "grabbing right");
                right.take(id, "right");
                System.out.println(this + " " + "grabbing left");
                left.take(id, "left");
                System.out.println(this + " " + "eating");
                pause();
                right.drop();
                left.drop();
            }
        }catch (InterruptedException e) {
            System.out.println(this + " " + "exiting via interrupt");
        }
    }

    public String toString() {
        return "Philosopher " + id;
    }
}
