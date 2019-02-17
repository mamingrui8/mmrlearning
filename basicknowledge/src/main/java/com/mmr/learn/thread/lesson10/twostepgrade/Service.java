package com.mmr.learn.thread.lesson10.twostepgrade;

/**
 * Description: TODO
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月17日 20:13
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class Service {
    Object object1 = new Object();
    public void methodA(){
        synchronized (object1){
            System.out.println("methodA() begin");
            boolean isContinueRun = true;
            while(isContinueRun){

            }
            System.out.println("methodB() end");
        }
    }

    Object objec2 = new Object();
    public void methodB(){
        synchronized (objec2){
            System.out.println("methodB() begin");
            System.out.println("methodB() end");
        }
    }
}
