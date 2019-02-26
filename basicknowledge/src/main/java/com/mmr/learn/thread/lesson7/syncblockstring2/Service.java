package com.mmr.learn.thread.lesson7.syncblockstring2;

import java.util.concurrent.TimeUnit;

/**
 *
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月17日 16:33
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class Service {
    String anyString = new String();
    public void a(String anyString){
        try{
            synchronized (this.anyString){
                this.anyString = anyString;
                System.out.println("a begin，thread name->" + Thread.currentThread().getName() + ", anyString->" + this.anyString);
                TimeUnit.SECONDS.sleep(5);
                System.out.println("a end，thread name->" + Thread.currentThread().getName() + ", anyString->" + this.anyString);
           }
        } catch (Exception e){
           e.printStackTrace();
        }
    }

    synchronized public void b(String anyString){
        System.out.println("b begin"+ ", anyString->" + anyString);
        this.anyString = anyString;
        System.out.println("b end" + ", anyString->" + anyString);
    }
}
