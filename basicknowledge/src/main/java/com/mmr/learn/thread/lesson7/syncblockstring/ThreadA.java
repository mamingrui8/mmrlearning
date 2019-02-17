package com.mmr.learn.thread.lesson7.syncblockstring;

/**
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月17日 15:49
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class ThreadA extends Thread{
    private Service service;
    public ThreadA(Service service){
        super();
        this.service = service;
    }

    @Override
    public void run(){
        service.setUsernamePassword("a", "aa");
    }
}
