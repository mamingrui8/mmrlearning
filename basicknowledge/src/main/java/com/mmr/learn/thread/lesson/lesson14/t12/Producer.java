package com.mmr.learn.thread.lesson.lesson14.t12;

/**
 * Description: 生产者/消费者模式 --- 生产者
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月26日 13:54
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class Producer {
    private String lock;

    public Producer(String lock){
        this.lock = lock;
    }

    /**
     * 生产商品
     */
    public void produce(){
        try {
            synchronized(lock){
                //若商品仍有库存，则停止生产商品
                if(!"".equals(ValueObject.value))
                    lock.wait();
                //System.nanoTime(): 返回当前jvm虚拟机运行所使用的源中的运行时间(纳秒)  不同的虚拟机可能使用不同的源
                String value = System.currentTimeMillis() + "_" + System.nanoTime();
                System.out.println("本次生产的商品值为: " +  value);
                ValueObject.value = value;
                //生产完毕后，通知阻塞的消费者线程恢复运行，消费商品
                lock.notify();
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
