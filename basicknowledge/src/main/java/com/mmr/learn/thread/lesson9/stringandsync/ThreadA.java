package com.mmr.learn.thread.lesson9.stringandsync;

/**
 *
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月17日 19:57
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class ThreadA extends Thread{
    @Override
    public void run(){
        Service.print("AAAAAAAAAAAAAAA");
    }
}
