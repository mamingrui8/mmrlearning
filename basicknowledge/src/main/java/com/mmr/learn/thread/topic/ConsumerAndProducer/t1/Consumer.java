package com.mmr.learn.thread.topic.ConsumerAndProducer.t1;

/**
 * Description: 消费者
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年03月03日 17:29
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class Consumer {
    public void consume(){
        try {
            if(Ordering.orderList.size() == 0){
                Thread.currentThread().wait();
            }else{
                Ordering.orderList.removeFirst(); //先进先出
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }
}
