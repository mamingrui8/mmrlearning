package com.mmr.learn.thread.lesson1;

/**
 *  多个线程共享数据  (同时访问同一个变量)
 */
public class Thread1 {
    public static void main(String[] args){
        Thread1_1 myThread = new Thread1_1();
        Thread t1 = new Thread(myThread, "A");
        Thread t2 = new Thread(myThread, "B");
        Thread t3 = new Thread(myThread, "C");
        Thread t4 = new Thread(myThread, "D");
        Thread t5 = new Thread(myThread, "E");

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
    }
}

class Thread1_1 extends Thread{
    private int count = 5;
    @Override
    public synchronized void run() { //先判断run()方法是否上锁，若有锁则进入等待队列
        super.run();
        count --;
        System.out.println("由" + Thread.currentThread().getName() + "计算得出->" + count);
    }
}
