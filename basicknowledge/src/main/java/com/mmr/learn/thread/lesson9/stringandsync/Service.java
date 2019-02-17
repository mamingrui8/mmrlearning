package com.mmr.learn.thread.lesson9.stringandsync;

import java.util.concurrent.TimeUnit;

/**
 * Description: TODO
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月17日 19:55
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class Service {
    public static void print(String param){
        try{
            synchronized (param){
                while(true){
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println(Thread.currentThread().getName() + " : " + param);
                }
            }
        } catch (Exception e){
           e.printStackTrace();
        }
    }
}
