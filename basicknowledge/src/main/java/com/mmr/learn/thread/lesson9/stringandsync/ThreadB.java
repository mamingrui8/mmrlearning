package com.mmr.learn.thread.lesson9.stringandsync;

/**
 * Description: TODO
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月17日 19:58
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class ThreadB extends Thread{
    @Override
    public void run(){
        Service.print("AAAAAAAAAAAAAAA");
    }
}