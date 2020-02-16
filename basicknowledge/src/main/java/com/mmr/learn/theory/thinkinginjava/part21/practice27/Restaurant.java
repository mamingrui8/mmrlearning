package com.mmr.learn.theory.thinkinginjava.part21.practice27;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 在Restaurant的基础上，将Synchronized替换成ReentrantLock
 * 做法: 服务员和厨师共用同一套锁
 */
public class Restaurant {
    ExecutorService exec = Executors.newCachedThreadPool();
    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();
    Meal meal;
    WaitPerson waitPerson = new WaitPerson(this);
    Chef chef = new Chef(this);

    public Restaurant() {
        exec.execute(waitPerson);
        exec.execute(chef);
    }

    public static void main(String[] args) {
        new Restaurant();
    }
}

/**
 * 餐窗
 */
class Meal {
    //当前做的菜是第几道菜
    private final int orderNum;
    public Meal(int orderNum) {
        this.orderNum = orderNum;
    }
    public String toString() {
        return "Meal " + orderNum;
    }
}

/**
 * 服务员
 */
class WaitPerson implements Runnable {
    private Restaurant restaurant;

    public WaitPerson(Restaurant r) {
        this.restaurant = r;
    }

    @Override
    public void run() {
        restaurant.lock.lock();
        try {
            while(!Thread.interrupted()) {
                while(restaurant.meal == null) {
                    //等待厨师炒菜
                    System.out.println("等待厨师炒菜");
                    restaurant.condition.await();
                }
                //本次拿到的菜,现在已经上完菜了
                System.out.println("WaitPerson got " + restaurant.meal);
                restaurant.meal = null;
                //唤醒所有等待的任务 由于当前只有两个任务，分别是服务员和厨师，因此不会出错，当前操作会唤醒厨师
                restaurant.condition.signalAll();
                TimeUnit.MILLISECONDS.sleep(100);
                System.out.println("WaitPerson sleep()");
            }
        } catch (InterruptedException e) {
            System.out.println("WaitPeron encountered interrupted");
        } finally {
            restaurant.lock.unlock();
        }
    }
}

/**
 * 厨师
 */
class Chef implements Runnable{
    private Restaurant restaurant;

    /**
     * 当前做的菜是第几道菜
     */
    private int count = 0;

    public Chef(Restaurant r) {
        this.restaurant = r;
    }

    @Override
    public void run() {
        restaurant.lock.lock();
        try {
            while(!Thread.interrupted()) {
                while(restaurant.meal != null) {
                    //等待服务员上菜
                    System.out.println("等待服务员上菜");
                    restaurant.condition.await();
                }
                if(++count == 10) {
                    System.out.println("菜已经做够了，本店歇业");
                    restaurant.exec.shutdownNow();
                }
                System.out.println("上菜喽");
                restaurant.meal = new Meal(count);
                restaurant.condition.signalAll();
                System.out.println("既释放了锁，又通知");
                TimeUnit.MILLISECONDS.sleep(100);
                System.out.println("Chef sleep()");
            }
        }catch (InterruptedException e) {
            System.out.println("Chef encountered interrupted");
        } finally {
            System.out.println("Chef unlock()");
            restaurant.lock.unlock();
        }
    }
}