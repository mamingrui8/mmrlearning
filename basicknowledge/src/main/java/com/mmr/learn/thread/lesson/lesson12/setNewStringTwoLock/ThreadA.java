package com.mmr.learn.thread.lesson.lesson12.setNewStringTwoLock;

/**
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月18日 22:41
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class ThreadA extends Thread{
    private MyService myService;
    public ThreadA(MyService myService){
        super();
        this.myService = myService;
    }
    @Override
    public void run(){
        myService.testMethod();
    }
}
