package com.mmr.learn.theory.thinkinginjava.part21.practice9;

/**
 * @author Charles Wesley
 * @date 2019/12/3 23:34
 */
public class SimplePriority2 implements Runnable{
    private int countDown = 5;
    private volatile double d;
    public String toString () {
        return Thread.currentThread() + ": " + countDown;
    }

    @Override
    public void run() {
        for(;;) {
            for (int i = 1; i < 100000; i++) {
                d += (Math.PI + Math.E) / (double)i;
                if(i % 1000 == 0) {
                    Thread.yield();
                }
            }
            System.out.println(this);
            if(--countDown == 0){
                return;
            }
        }
    }
}
