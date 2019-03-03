package com.mmr.learn.thread.lesson18.t7;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Description: TODO
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年03月03日 22:04
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class MyService {
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private boolean hasValue = false;

    public void setValue(){
        try {
            System.out.println("你好->" + Thread.currentThread().getName());
            lock.lock();
            if(hasValue) {
                System.out.println(" 有可能连续☆☆");
                condition.await();
            }
            System.out.println("打印☆");
            hasValue = true;
            condition.signal();
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally{
            System.out.println("释放锁");
            lock.unlock();
        }
    }

    public void getValue(){
        try {
            lock.lock();
            if(!hasValue) {
                System.out.println("有可能连续★★");
                condition.await();
            }
            System.out.println("打印★");
            hasValue = false;
            condition.signal();
        }catch (InterruptedException e){
            e.printStackTrace();
        } finally{
            lock.unlock();
        }
    }
}
