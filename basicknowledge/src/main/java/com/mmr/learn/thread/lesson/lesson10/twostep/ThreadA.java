package com.mmr.learn.thread.lesson.lesson10.twostep;

/**
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月17日 20:16
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class ThreadA extends Thread{
    private Service service;
    public ThreadA(Service service){
        super();
        this.service = service;
    }
}
