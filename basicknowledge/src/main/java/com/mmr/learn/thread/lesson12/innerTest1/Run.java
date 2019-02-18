package com.mmr.learn.thread.lesson12.innerTest1;

/**
 * Description: 内置类与锁
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月18日 17:14
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class Run {
    public static void main(String[] args){
        final OutClass.InnerClass innerClass = new OutClass.InnerClass();
        Thread t1 = new Thread(new Runnable(){
            @Override
            public void run(){
                innerClass.method1();
            }
        }, "A");
        Thread t2 = new Thread(innerClass :: method2, "B"); //这里用到了jdk1.8 lambda表达式
        t1.start();
        t2.start();
    }
}
