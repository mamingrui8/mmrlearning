package com.mmr.learn.thread.lesson.lesson1;

/**
 * Description: 注意观察以下线程的名称
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月03日 22:38
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class Thread3 {
    public static void main(String[] args) {
        Thread3_1 thread3_1 = new Thread3_1();
        Thread t1  = new Thread(thread3_1);
        t1.setName("你好"); //这里只不过设置了ti的线程名称，和Thread3_1没有半毛钱关系
        t1.start();
    }
}

class Thread3_1 extends Thread{
    public Thread3_1(){
        System.out.println("Thread.name->" + Thread.currentThread().getName());
        System.out.println("name->" + this.getName());
    }

    @Override
    public void run(){
        System.out.println("Thread.name...->" + Thread.currentThread().getName());
        System.out.println("name...->" + this.getName());
    }
}
