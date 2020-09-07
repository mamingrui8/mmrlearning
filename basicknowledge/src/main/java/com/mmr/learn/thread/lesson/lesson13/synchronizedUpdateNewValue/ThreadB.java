package com.mmr.learn.thread.lesson.lesson13.synchronizedUpdateNewValue;

/**
 * Description: synchronized代码块具有volatile同步的功能
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月22日 16:04
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
        service.stopMethod();
    }
}
