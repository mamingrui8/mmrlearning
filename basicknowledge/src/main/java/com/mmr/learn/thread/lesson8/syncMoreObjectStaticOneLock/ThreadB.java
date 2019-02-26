package com.mmr.learn.thread.lesson8.syncMoreObjectStaticOneLock;

import com.mmr.learn.thread.lesson8.syncMoreObjectStaticOneLock.Service;

/**
 *
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月17日 17:27
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
        service.printB();
    }
}
