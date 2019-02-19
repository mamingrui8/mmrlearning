package com.mmr.learn.thread.lesson13.test123;

import java.util.concurrent.TimeUnit;

/**
 * Description: TODO
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月19日 15:02
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class Run {
    public static void main(String[] args) throws InterruptedException{
        MyService myService = new MyService();
        MyThread[] myThreads = new MyThread[5];
        for(int i=0; i<myThreads.length; i++){
            myThreads[i] = new MyThread(myService);
        }
        for(int i=0; i<myThreads.length; i++){
            myThreads[i].start();
        }
        TimeUnit.SECONDS.sleep(1);
        System.out.println(MyService.count);
    }
}
