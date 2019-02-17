package com.mmr.learn.thread.lesson8.synStaticMethod;

/**
 * Description: TODO
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月17日 17:27
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class ThreadB extends Thread{
    @Override
    public void run(){
        Service.printB();
    }
}
