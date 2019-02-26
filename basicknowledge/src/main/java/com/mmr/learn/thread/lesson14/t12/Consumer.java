package com.mmr.learn.thread.lesson14.t12;

/**
 * Description: 生产者/消费者模式 --- 消费者
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月26日 14:01
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class Consumer {
    private String lock;

    public Consumer(String lock){
        this.lock = lock;
    }

    public void consume(){
        try {
            synchronized (lock){
                //若当前商品没有库存了，则消费者线程阻塞
                if("".equals(ValueObject.value)){
                    lock.wait();
                }
                System.out.println("本次消费的商品值为: " + ValueObject.value);
                ValueObject.value = "";
                //通知生产者进货啦
                lock.notify();
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
