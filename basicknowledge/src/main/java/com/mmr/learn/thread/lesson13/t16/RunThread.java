package com.mmr.learn.thread.lesson13.t16;

/**
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月19日 10:29
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class RunThread extends Thread{
    private boolean isRunning = true;
    public boolean isRunning(){
        return this.isRunning;
    }
    public void setRunning(boolean isRunning){
        this.isRunning = isRunning;
    }
    @Override
    public void run(){
        System.out.println("进入run了");
        while(isRunning){}
        System.out.println("线程被停止了");
    }
}
