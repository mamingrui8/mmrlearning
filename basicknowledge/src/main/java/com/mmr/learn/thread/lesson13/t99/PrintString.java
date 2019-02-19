package com.mmr.learn.thread.lesson13.t99;

import java.util.concurrent.TimeUnit;

/**
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月19日 10:05
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class PrintString {
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
}
