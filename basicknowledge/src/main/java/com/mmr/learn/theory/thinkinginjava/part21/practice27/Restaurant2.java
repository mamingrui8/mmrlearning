package com.mmr.learn.theory.thinkinginjava.part21.practice27;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 在Restaurant的基础上，将Synchronized替换成ReentrantLock
 * 做法: 服务员和厨师各自用自己的锁
 */
public class Restaurant2 {
    ExecutorService exec = Executors.newCachedThreadPool();
    Meal2 meal2;
    WaitPerson2 waitPerson2 = new WaitPerson2(this);
    Chef2 chef2 = new Chef2(this);

    public Restaurant2() {
        exec.execute(waitPerson2);
        exec.execute(chef2);
    }

    public static void main(String[] args) {
        new Restaurant2();
    }
}

/**
 * 餐窗
 */
class Meal2 {
    //当前做的菜是第几道菜
    private final int orderNum;
    public Meal2(int orderNum) {
        this.orderNum = orderNum;
    }
    public String toString() {
        return "Meal " + orderNum;
    }
}

/**
 * 服务员
 */
class WaitPerson2 implements Runnable{
    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();
    private Restaurant2 restaurant2;

    public WaitPerson2(Restaurant2 r) {
        this.restaurant2 = r;
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()) {
                lock.lock();
                try {
                    while(restaurant2.meal2 == null) {
                        System.out.println("没菜了，请厨师做菜");
                        condition.await();
                    }
                } finally {
                    lock.unlock();
                }

                System.out.println("出餐 " + restaurant2.meal2);
                restaurant2.meal2 = null;
                restaurant2.chef2.lock.lock();
                try {
                    restaurant2.chef2.condition.signalAll();
                } finally {
                    restaurant2.chef2.lock.unlock();
                }
            }
        } catch (InterruptedException e) {
            System.out.println("WaitPerson2 encountered interrupt");
        }
    }
}

class Chef2 implements Runnable {
    Lock lock = new ReentrantLock();Condition condition = lock.newCondition();
    private int count = 0;
    private Restaurant2 restaurant2;
    public Chef2(Restaurant2 r) {
        this.restaurant2 = r;
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()) {
                lock.lock();
                try {
                    while(restaurant2.meal2 != null) {
                        System.out.println("有菜尚未出餐");
                        condition.await();
                    }
                }finally {
                    lock.unlock();
                }
                if(++count == 10) {
                    System.out.println("饭店打烊了");
                    restaurant2.exec.shutdownNow();
                }
                restaurant2.waitPerson2.lock.lock();
                try {
                    restaurant2.meal2 = new Meal2(count);
                    restaurant2.waitPerson2.condition.signalAll();
                } finally {
                    restaurant2.waitPerson2.lock.unlock();
                }

                TimeUnit.MILLISECONDS.sleep(100);
            }
        } catch (InterruptedException e) {
            System.out.println("Chef2 encountered interrupt");
        }
    }
}