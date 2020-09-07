package com.mmr.learn.thread.lesson.lesson8.syncMoreObjectStaticOneLock;

import java.util.concurrent.TimeUnit;

/**
 *
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月17日 17:56
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class Service {
    synchronized public static void printA(){
        try{
            System.out.println("线程名称为: " + Thread.currentThread().getName() + " 在 " + System.currentTimeMillis() + " 进入printA()");
            TimeUnit.SECONDS.sleep(3);
            System.out.println("线程名称为: " + Thread.currentThread().getName() + " 在 " + System.currentTimeMillis() + " 离开printA()");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    synchronized public static void printB(){
        System.out.println("线程名称为: " + Thread.currentThread().getName() + " 在 " + System.currentTimeMillis() + " 进入printB()");
        System.out.println("线程名称为: " + Thread.currentThread().getName() + " 在 " + System.currentTimeMillis() + " 离开printB()");
    }
}
