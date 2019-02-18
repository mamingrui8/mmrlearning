package com.mmr.learn.thread.lesson12.innerTest2;

import java.util.concurrent.TimeUnit;

/**
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月18日 17:25
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
@SuppressWarnings("Duplicates")
public class OutClass {
    static class InnerClass1 {
        /**
         * 锁加在InnerClass2类的对象上，由外界传入
         */
        public void method1(InnerClass2 class2){
            String threadName = Thread.currentThread().getName();
            synchronized (class2){
                System.out.println(threadName + " 进入InnerClass1类中的method1方法");
                for(int i = 0; i < 10; i++){
                    System.out.println("i=" + i);
                    try{
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (InterruptedException e){
                       e.printStackTrace();
                    }
                }
                System.out.println(threadName + " 离开InnerClass1类中的method1方法");
            }
        }

        /**
         *  锁加在方法上，也即InnerClass1的this对象上
         */
        public synchronized void method2(){
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " 进入InnerClass1类中的method2方法");
            for(int i = 0; i < 10; i++){
                System.out.println("i=" + i);
                try{
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            System.out.println(threadName + " 离开InnerClass1类中的method2方法");
        }
    }

    static class InnerClass2 {
        public synchronized void method1(){
            System.out.println(Thread.currentThread().getName() + " 进入InnerClass2类中的method1方法");
            for(int k = 0; k<10; k++){
                System.out.println("k=" + k);
                try{
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e){
                   e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + " 离开InnerClass2类中的method1方法");
        }
    }
}
