package com.mmr.learn.thread.lesson13.test123;

/**
 * Description: TODO
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月19日 14:57
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class MyService {
    public static Integer count = new Integer(0);
    synchronized public void addNum(){
        count = count+100;
        System.out.println(Thread.currentThread().getName() + "  加了100之后的值是：" + count);
    }
}
