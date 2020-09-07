package com.mmr.learn.thread.lesson.lesson13.t10;

import java.util.concurrent.TimeUnit;

/**
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月19日 10:14
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class PrintString implements Runnable{
    private boolean isContinuePrint = true;
    public boolean isContinuePrint() {
        return isContinuePrint;
    }
    public void setContinuePrint(boolean isContinuePrint){
        this.isContinuePrint = isContinuePrint;
    }
    public void printStringMethod(){
        try{
            while(isContinuePrint){
                System.out.println("run printStringMethod threadName=" + Thread.currentThread().getName());
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        printStringMethod();
    }
}
