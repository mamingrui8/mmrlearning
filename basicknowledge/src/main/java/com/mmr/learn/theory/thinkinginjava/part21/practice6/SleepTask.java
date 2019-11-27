package com.mmr.learn.theory.thinkinginjava.part21.practice6;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * @author Charles Wesley
 * @date 2019/11/27 22:25
 */
public class SleepTask implements Callable<Integer> {

    @Override
    public Integer call() {
        Random rand = new Random();
        int sleepTime = rand.nextInt(10);
        try {
            TimeUnit.SECONDS.sleep(sleepTime);
        } catch (InterruptedException e) {
            System.out.println("InterruptedException");
        }

        return sleepTime;
    }
}
