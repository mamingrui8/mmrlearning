package com.mmr.learn.thread.lesson.lesson12.innerTest1;

import java.util.concurrent.TimeUnit;

/**
 * Description: 内置类与同步
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月18日 17:09
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
@SuppressWarnings("Duplicates")
public class OutClass {
    /**
     * 定义静态内置类
     */
    static class InnerClass {
        /**
         * 定义在字符串上的锁
         */
        public void method1(){
            synchronized("其他锁"){
                for(int i =0; i<10; i++){
                    System.out.println(Thread.currentThread().getName() + " i=" + i);
                    try{
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e){
                       e.printStackTrace();
                    }
                }
            }
        }

        /**
         * 定义在方法上的锁
         */
        synchronized public void method2(){
            for(int i = 11; i <= 20; i++){
                System.out.println(Thread.currentThread().getName() + " i=" + i);
                try{
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
