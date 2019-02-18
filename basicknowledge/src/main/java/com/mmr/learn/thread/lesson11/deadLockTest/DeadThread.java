package com.mmr.learn.thread.lesson11.deadLockTest;

import java.util.concurrent.TimeUnit;

/**
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月18日 16:22
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class DeadThread implements Runnable{
    private String username;
    private Object lock1 = new Object();
    private Object lock2 = new Object();
    public void setFlag(String username){
        this.username = username;
    }
    @Override
    public void run(){
        if ("a".equals(username)){
            synchronized (lock1){
                try{
                    System.out.println("username = " + username);
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e){
                   e.printStackTrace();
                }
                synchronized (lock2){
                    System.out.println("按照lock2->lock1的顺序执行了");
                }
            }
        }
        if("b".equals(username)){
            synchronized (lock2){
                try{
                    System.out.println("username = " + username);
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                synchronized (lock1){
                    System.out.println("按照lock1->lock2的顺序执行了");
                }
            }
        }
    }
}
