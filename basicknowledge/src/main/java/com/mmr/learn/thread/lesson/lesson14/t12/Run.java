package com.mmr.learn.thread.lesson.lesson14.t12;

/**
 * Description: 生产者/消费者模式 --- 运行类
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月26日 14:10
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class Run {
    public static void main(String[] args){
        String lock = new String("");
        Producer producer = new Producer(lock);
        Consumer consumer = new Consumer(lock);

        ThreadProducer threadProducer = new ThreadProducer(producer);
        ThreadConsumer threadConsumer = new ThreadConsumer(consumer);

        threadProducer.start();
        threadConsumer.start();
    }
}
