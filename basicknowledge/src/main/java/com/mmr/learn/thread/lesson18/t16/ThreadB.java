package com.mmr.learn.thread.lesson18.t16;

/**
 * Description: TODO
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年03月11日 23:45
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class ThreadB extends Thread{
    private MyService service;

    public ThreadB(MyService service){
        super();
        this.service = service;
    }

    @Override
    public void run(){
        service.notifyMethod();
    }
}
