package com.mmr.learn.theory.thinkinginjava.part21.practice14;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * @author Charles Wesley
 * @date 2019/12/14 21:15
 */
public class E14_ManyTimers {
    public static void main(String[] args) throws Exception{
        if (args.length < 1) {
            System.err.println("Usage: java.E14_ManyTimers <num of timers>");
        }
        int numOfTimers = Integer.parseInt(args[0]);
        for (int i = 0; i < numOfTimers; i++) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.println(System.currentTimeMillis());
                }
            },numOfTimers - i);
        }
        //Wait for timers to expire
        TimeUnit.SECONDS.sleep(2 * numOfTimers);
        System.exit(0);
    }
}
