package com.mmr.learn.thread.lesson.lesson14.t12;

/**
 * Description: 生产者/消费者模式 --- 生产者线程
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月26日 14:08
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class ThreadProducer extends Thread{
    private Producer producer;

    public ThreadProducer(Producer producer){
        this.producer = producer;
    }

    @Override
    public void run(){
        while(true){
            producer.produce();
        }
    }
}
