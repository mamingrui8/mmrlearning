package com.mmr.learn.thread.lesson.lesson1;

import java.util.Random;

/**
 * Description: 线程优先级
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月14日 11:21
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class Thread7 {
    /*
        优先级较高的线程CPU会优先执行。
        设置线程优先级有助于帮助"线程规划器"确定下一次选择哪一个线程去执行。
     */
    public static void main(String[] args){
        Thread7 thread7 = new Thread7();
        thread7.test4();
    }

    /**
     * 线程优先级的继承特性
     * 共有三种预定义常量值
     * public final static int MIN_PRIORITY = 1;
     * public final static int NORM_PRIORITY = 5;
     * public final static int MAX_PRIORITY = 10;
     *
     * test1()执行后会发现输出都是10，原因很简单，Thread7_2依赖Thread7_1启动，Thread7_1依赖main Thread启动，
     * 而main Thread的priority被我设置成了10。
     */
    public void test1(){
        System.out.println("主线程 priority=" + Thread.currentThread().getPriority());
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        System.out.println("主线程 priority=" + Thread.currentThread().getPriority());
        Thread7_1 thread7_1 = new Thread7_1();
        thread7_1.start();
    }

    /**
     * 优先级具有规则性
     *
     * 从test2()不难看出:
     * 1. 高优先级的线程总是会优先执行完，但不代表优先级高的线程会全部先执行完毕。
     * 2. 线程的执行顺序、被分配CPU时间片的机会与线程start()启动的顺序没有任何关系。
     *
     * 也即，CPU尽量将执行资源让给优先级较高的线程。
     */
    public void test2(){
        for (int i = 0; i < 5; i++) {
            Thread7_3 thread7_3 = new Thread7_3();
            thread7_3.setPriority(1);
            thread7_3.start();
            Thread7_4 thread7_4 = new Thread7_4();
            thread7_4.setPriority(Thread.MIN_PRIORITY);
            thread7_4.start();
        }
    }

    /**
     * 优先级具有随机性
     *
     * test3()中，Thread7_3比Thread7_4优先级低，但从执行结果来看，thread7_3居然先于thread7_4执行完毕。
     * 那么这又是为什么呢？
     * 原来优先级的规则性并不是一定的，优先级还具有随机性。
     * 也即，优先级高的线程并不一定每一次都先执行完run()方法中的任务。
     */
    public void test3(){
        for (int i = 0; i < 6; i++) {
            Thread7_3 thread7_3 = new Thread7_3();
            thread7_3.setPriority(5);
            thread7_3.start();
            Thread7_4 thread7_4 = new Thread7_4();
            thread7_4.setPriority(6);
            thread7_4.start();
        }
    }

    /**
     * 优先级越高，享受到的CPU资源越多---> 执行速度越快
     *
     * test4()中，b比a的优先级高，因此执行速度更快。
     */
    public void test4(){
        Thread7_5 a = new Thread7_5();
        a.setPriority(Thread.NORM_PRIORITY - 3);
        a.start();
        Thread7_6 b = new Thread7_6();
        b.setPriority(Thread.NORM_PRIORITY + 3);
        try {
            Thread.sleep(5000); //让a和b两个线程执行20秒
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        b.start();
        a.stop();
        b.stop();
        System.out.println("a=" + a.getCount());
        System.out.println("b=" + b.getCount());
    }

}

class Thread7_1 extends Thread{
    @Override
    public void run(){
        System.out.println("Thread7_1 priority=" + this.getPriority());
        //Thread7_2依赖Thread7_1来启动
        Thread7_2 thread7_2 = new Thread7_2();
        thread7_2.start();
    }
}

class Thread7_2 extends Thread{
    @Override
    public void run(){
        System.out.println("Thread7_2 priority=" + this.getPriority());
    }
}

class Thread7_3 extends Thread{
    @Override
    public void run(){
        long beginTime = System.currentTimeMillis();
        long addResult = 0;
        for(int i=0; i< 50000; i++){
            Random random = new Random();
            random.nextInt();
            addResult = addResult + i;
        }
        long endTime = System.currentTimeMillis();
        System.out.println("★ ★ ★ ★ ★ thread1 use time =" + (endTime-beginTime) + "毫秒");
    }
}

class Thread7_4 extends Thread{
    @Override
    public void run(){
        long beginTime = System.currentTimeMillis();
        long addResult = 0;
        for(int i=0; i< 50000; i++){
            Random random = new Random();
            random.nextInt();
            addResult = addResult + i;
        }
        long endTime = System.currentTimeMillis();
        System.out.println("☆ ☆ ☆ ☆ ☆ thread1 use time =" + (endTime-beginTime) + "毫秒");
    }
}

class Thread7_5 extends Thread{
    private int count = 0;
    public int getCount(){
        return count;
    }
    @Override
    public void run(){
        while(true){
            count++;
        }
    }
}

class Thread7_6 extends Thread{
    private int count = 0;
    public int getCount(){
        return count;
    }
    @Override
    public void run(){
        while(true){
            count++;
        }
    }
}