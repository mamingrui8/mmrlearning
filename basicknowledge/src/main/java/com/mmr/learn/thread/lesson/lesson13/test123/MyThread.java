package com.mmr.learn.thread.lesson.lesson13.test123;

/**
 *
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月19日 14:58
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class MyThread extends Thread{
    public MyService myService;

    public MyThread(MyService myService){
        super();
        this.myService = myService;
    }

    @Override
    public void run(){
        myService.addNum();
    }
}
