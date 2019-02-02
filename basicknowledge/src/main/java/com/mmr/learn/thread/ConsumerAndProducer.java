package com.mmr.learn.thread;

import java.util.LinkedList;

/**
 * Description: 经典的生产者、消费者问题
 *
 * 概念:
 *
 * 对于此模型，应该明确一下几点：
 * 1、生产者仅仅在仓储未满时候生产，仓满则停止生产。
 * 2、消费者仅仅在仓储有产品时候才能消费，仓空则等待。
 * 3、当消费者发现仓储没产品可消费时候会通知生产者生产。
 * 4、生产者在生产出可消费产品时候，应该通知等待的消费者去消费。
 */
public class ConsumerAndProducer {
    public static void main(String[] args){
        Storage storage = new Storage();
        Producer producer = new Producer(storage);
        producer.setNum(10);
        Consumer consumer = new Consumer(storage);
        consumer.setNum(50);

        producer.start();
        consumer.start();
    }

}


class Storage
{
    // 仓库最大存储量
    private final int MAX_SIZE = 100;
    // 仓库存储的载体
    private LinkedList<Object> list = new LinkedList<Object>();
    // 生产num个产品
    public void produce(int num){
        // 同步代码段
        synchronized (list){
            // 如果仓库剩余容量不足
            while (list.size() + num > MAX_SIZE){
                System.out.println("【要生产的产品数量】:" + num + "/t【库存量】:"
                        + list.size() + "/t暂时不能执行生产任务!");
                try{
                    // 由于条件不满足，生产阻塞
                    list.wait();
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            // 生产条件满足情况下，生产num个产品
            for (int i = 1; i <= num; ++i){
                list.add(new Object());
            }
            System.out.println("【已经生产产品数】:" + num + "/t【现仓储量为】:" + list.size());
            list.notifyAll();
        }
    }

    // 消费num个产品
    public void consume(int num)
    {
        // 同步代码段
        synchronized (list)
        {
            // 如果仓库存储量不足
            while (list.size() < num)
            {
                System.out.println("【要消费的产品数量】:" + num + "/t【库存量】:"
                        + list.size() + "/t暂时不能执行生产任务!");
                try
                {
                    // 由于条件不满足，消费阻塞
                    list.wait();
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }

            // 消费条件满足情况下，消费num个产品
            for (int i = 1; i <= num; ++i)
            {
                list.remove();
            }

            System.out.println("【已经消费产品数】:" + num + "/t【现仓储量为】:" + list.size());

            list.notifyAll();
        }
    }

    // get/set方法
    public LinkedList<Object> getList()
    {
        return list;
    }

    public void setList(LinkedList<Object> list)
    {
        this.list = list;
    }

    public int getMAX_SIZE()
    {
        return MAX_SIZE;
    }
}


/**
 * 生产者类Producer继承线程类Thread
 */
class Producer extends Thread
{
    // 每次生产的产品数量
    private int num;

    // 所在放置的仓库
    private Storage storage;

    // 构造函数，设置仓库
    public Producer(Storage storage)
    {
        this.storage = storage;
    }

    // 线程run函数
    public void run()
    {
        produce(num);
    }

    // 调用仓库Storage的生产函数
    public void produce(int num)
    {
        storage.produce(num);
    }

    // get/set方法
    public int getNum()
    {
        return num;
    }

    public void setNum(int num)
    {
        this.num = num;
    }

    public Storage getStorage()
    {
        return storage;
    }

    public void setStorage(Storage storage)
    {
        this.storage = storage;
    }
}
/**
 * 消费者类Consumer继承线程类Thread
 */
class Consumer extends Thread
{
    // 每次消费的产品数量
    private int num;

    // 所在放置的仓库
    private Storage storage;

    // 构造函数，设置仓库
    public Consumer(Storage storage)
    {
        this.storage = storage;
    }

    // 线程run函数
    public void run()
    {
        consume(num);
    }

    // 调用仓库Storage的生产函数
    public void consume(int num)
    {
        storage.consume(num);
    }

    // get/set方法
    public int getNum()
    {
        return num;
    }

    public void setNum(int num)
    {
        this.num = num;
    }

    public Storage getStorage()
    {
        return storage;
    }

    public void setStorage(Storage storage)
    {
        this.storage = storage;
    }
}