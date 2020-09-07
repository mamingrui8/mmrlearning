package com.mmr.learn.thread.lesson.lesson10.twostep;

/**
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月17日 20:17
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class ThreadB extends Thread{
    private Service service;
    public ThreadB(Service service){
        super();
        this.service = service;
    }
}
