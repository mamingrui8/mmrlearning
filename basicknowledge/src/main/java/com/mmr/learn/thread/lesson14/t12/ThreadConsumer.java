package com.mmr.learn.thread.lesson14.t12;

/**
 * Description: 生产者/消费者模式 --- 消费者线程
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月26日 14:05
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class ThreadConsumer extends Thread{
    private Consumer consumer;

    public ThreadConsumer(Consumer consumer){
        this.consumer = consumer;
    }

    @Override
    public void run(){
        while(true){
            consumer.consume();
        }
    }
}
