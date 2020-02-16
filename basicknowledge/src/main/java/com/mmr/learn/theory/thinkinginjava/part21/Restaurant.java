package com.mmr.learn.theory.thinkinginjava.part21;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * "厨师与服务员"建模代码
 */
public class Restaurant {
    Meal meal;
    ExecutorService exec = Executors.newCachedThreadPool();
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
        try {
            while(!Thread.interrupted()) {
                synchronized (this) {
                    while(restaurant.meal == null) {
                        //等待厨师做菜
                        wait();
                    }
                }
                //本次拿到的菜,现在已经上完菜了
                System.out.println("WaitPerson got " + restaurant.meal);
                synchronized (restaurant.chef) {
                    //清空点菜器
                    restaurant.meal = null;
                    //唤醒厨师
                    restaurant.chef.notifyAll();
                }
            }
        }catch (InterruptedException e) {
            System.out.println("WaitPerson interrupted");
        }
    }
}

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
        try {
            while(!Thread.interrupted()) {
                synchronized (this) {
                    while(restaurant.meal != null) {
                        //餐窗内存在尚未出餐的食物，等待服务员端走后，再做菜
                        wait();
                    }
                }
                if(++count == 10) {
                    System.out.println("Out of food, closing");
                    restaurant.exec.shutdownNow();
                    //return; //若加上return，则Chef任务立刻退出，不会执行后续语句，直到sleep()抛出异常才停止运行。
                }
                //上菜啦
                System.out.println("Order up!  ");
                synchronized (restaurant.waitPerson) {
                    restaurant.meal = new Meal(count);
                    restaurant.waitPerson.notifyAll();
                }
                //在执行完restaurant.exec.shutdownNow()后，再次执行sleep()时会抛出InterruptedException异常
                //若去掉sleep()，则循环会回到while()开头处，由于Thread.interrupted()逻辑判断不通过，最终退出run()
                TimeUnit.MILLISECONDS.sleep(100);
            }
        }catch(InterruptedException e) {
            System.out.println("Chef interrupted");
        }
    }
}