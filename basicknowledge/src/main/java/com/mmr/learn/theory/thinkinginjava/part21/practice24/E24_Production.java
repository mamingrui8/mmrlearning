package com.mmr.learn.theory.thinkinginjava.part21.practice24;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 单个生产者、单个消费者模型
 */
public class E24_Production {
    ExecutorService exec = Executors.newCachedThreadPool();
    Consumer consumer = new Consumer(this);
    Producer producer = new Producer(this);
    Repo repo;

    public E24_Production() {
        exec.execute(consumer);
        exec.execute(producer);
    }

    public static void main(String[] args) {
        new E24_Production();
    }
}

/**
 * 存放商品的仓库
 */
class Repo {
    //当前商品是第几个商品
    private final int productNum;

    public Repo(int productNum) {
        this.productNum = productNum;
    }

    public String toString() {
        return "Product " + productNum;
    }
}

/**
 * 消费者
 */
class Consumer implements Runnable{
    private E24_Production e24_production;
    public Consumer(E24_Production e24_production) {
        this.e24_production = e24_production;
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()) {
                synchronized (this) {
                    while(this.e24_production.repo == null) {
                        //仓库中没有货物可以提取了，因此等待生产者生产商品
                        this.wait();
                    }
                }
                System.out.println("Consumer got " + this.e24_production.repo);
                synchronized (this.e24_production.producer) { //此处如果换成this，能否正常运行呢？ 答: 不能。如果想唤醒线程，则首先必须获得线程释放的锁。
                    //清空仓库 (因为货物出仓了)
                    this.e24_production.repo = null;
                    //唤醒生产者，继续生产商品
                    this.e24_production.producer.notifyAll();
                }
            }
        }catch (InterruptedException e) {
            System.out.println("Consumer encountered interrupt");
        }

    }
}

/**
 * 生产者
 */
class Producer implements Runnable{
    /**
     * 当前生产出的商品是第几个商品
     */
    private Integer count = 0;
    private E24_Production e24_production;
    public Producer(E24_Production e24_production) {
        this.e24_production = e24_production;
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()) {
                synchronized (this) {
                    if(e24_production.repo != null) {
                        //仓库中有商品尚未被消费，因此等待消费者消费商品
                        this.wait();
                    }
                }
                if(++count == 10) {
                    System.out.println("Out of product, Closing");
                    this.e24_production.exec.shutdownNow();
                }
                System.out.println("Producer produce product");
                synchronized (this.e24_production.consumer) {
                    this.e24_production.repo = new Repo(count);
                    this.e24_production.consumer.notifyAll();
                }
                //此处添加暂停操作，是为了在运行了shutdownNow()后，本轮操作能够抛出中断异常，间接的停止执行当前任务
                TimeUnit.MILLISECONDS.sleep(500);
            }
        } catch (InterruptedException e) {
            System.out.println("Producer encountered interrupt");
        }
    }
}
