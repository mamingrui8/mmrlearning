package com.mmr.learn.thread.lesson12.setNewStringTwoLock;

/**
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月18日 22:38
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class MyService {
    private String lock = "123";
    public void testMethod(){
        try{
            synchronized (lock){
                System.out.println(Thread.currentThread().getName() + " begin " + System.currentTimeMillis());
                lock = "456";
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName() + " end " + System.currentTimeMillis());
            }
        } catch (InterruptedException e){
           e.printStackTrace();
        }
    }
}
