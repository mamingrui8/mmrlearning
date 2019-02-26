package com.mmr.learn.thread.lesson8.synStaticMethod;

/**
 *
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月17日 17:26
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class ThreadA extends Thread{
    @Override
    public void run(){
        Service.printA();
    }
}
