package com.mmr.learn.thread.lesson10.twostep;

/**
 * Description: TODO
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月17日 20:13
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class Service {
    public synchronized void methodA(){
        System.out.println("methodA() begin");
        boolean isContinueRun = true;
        while(isContinueRun){

        }
        System.out.println("methodB() end");
    }

    public synchronized void methodB(){
        System.out.println("methodB() begin");
        System.out.println("methodB() end");
    }
}
