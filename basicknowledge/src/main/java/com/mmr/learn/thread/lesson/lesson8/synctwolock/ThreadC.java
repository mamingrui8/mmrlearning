package com.mmr.learn.thread.lesson.lesson8.synctwolock;

/**
 *
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月17日 17:34
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class ThreadC extends Thread{
    private Service service;

    public ThreadC(Service service){
        this.service = service;
    }

    @Override
    public void run(){
        service.printC();
    }
}
