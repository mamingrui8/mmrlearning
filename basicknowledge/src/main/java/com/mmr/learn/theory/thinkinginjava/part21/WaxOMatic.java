package com.mmr.learn.theory.thinkinginjava.part21;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class WaxOMatic {
    public static void main(String[] args) throws Exception{
        Car car = new Car();
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new WaxOff(car));
        exec.execute(new WaxOn(car));
        TimeUnit.SECONDS.sleep(5);
        exec.shutdownNow();
    }
}

class Car {
    /**
     * 是否被涂蜡
     */
    private boolean waxOn = false;

    /**
     * 涂蜡
     */
    public synchronized void waxed() {
        //Ready to buff
        waxOn = true;
        //唤醒所有曾经持有Car对象锁，因wait()释放锁导致阻塞的线程。
        //notifyAll()可以换成notify() 因为我在main()中只创建了2个任务，根据业务逻辑，同一时刻只会有一个任务被阻塞
        notifyAll();
    }

    /**
     * 等待涂蜡
     */
    public synchronized void waitForWaxing() throws InterruptedException{
        while(waxOn == true) {
            wait();
        }
    }

    /**
     * 抛光
     */
    public synchronized void buffed() {
        waxOn = false;
        notifyAll();
    }

    /**
     * 等待抛光
     */
    public synchronized void waitForBuffed()throws InterruptedException{
        while (waxOn == false) {
            wait();
        }
    }
}

/**
 * 上蜡
 */
class WaxOn implements Runnable {
    private Car car;
    public WaxOn(Car c) {
        this.car = c;
    }
    @Override
    public void run() {
        try {
            System.out.println("Enter WaxOn run()");
            while(!Thread.interrupted()) {
                System.out.println("Wax On!   ");
                TimeUnit.MILLISECONDS.sleep(200);
                //涂蜡 唤醒抛光任务
                car.waxed();
                //进入阻塞状态，等待抛光任务完成，重新上蜡
                car.waitForWaxing();
            }
        }catch (InterruptedException e) {
            System.out.println("Exiting via interrupt");
        }
        System.out.println("Ending Wax On task");
    }
}

/**
 * 抛光
 */
class WaxOff implements Runnable{
    private Car car;
    public WaxOff(Car c) {
        this.car = c;
    }
    @Override
    public void run() {
        try {
            System.out.println("Enter WaxOff run()");
            while(!Thread.interrupted()) {
                //抛光任务完成
                car.buffed();
                System.out.println("Wax Off");
                TimeUnit.MILLISECONDS.sleep(200);
                //等待重新抛光
                car.waitForBuffed();
            }
        }catch (InterruptedException e) {
            System.out.println("Exiting via interrupt");
        }
        System.out.println("Ending Wax Off task");
    }
}