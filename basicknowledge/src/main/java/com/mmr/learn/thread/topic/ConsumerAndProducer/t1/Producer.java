package com.mmr.learn.thread.topic.ConsumerAndProducer.t1;

import java.util.concurrent.TimeUnit;

/**
 * Description: 生产者
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年03月03日 17:29
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class Producer {
    /**
     * 生产商品
     */
    public void produce(String orderName){
        try {
            if(Ordering.MAX_VOLUME <= Ordering.orderList.size()){
                Thread.currentThread().wait();
            }else{
                Ordering.orderList.add(orderName);
                notifyAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
