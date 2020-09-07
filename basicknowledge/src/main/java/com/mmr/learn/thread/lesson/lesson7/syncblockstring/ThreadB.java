package com.mmr.learn.thread.lesson.lesson7.syncblockstring;

/**
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月17日 16:04
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class ThreadB extends Thread{
    private Service service;
    public ThreadB(Service service){
        super();
        this.service = service;
    }

    @Override
    public void run(){
        service.setUsernamePassword("b", "bb");
    }
}
