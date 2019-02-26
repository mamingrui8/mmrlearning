package com.mmr.learn.thread.lesson6.t5;

import java.util.concurrent.TimeUnit;

/**
 *
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月14日 22:41
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class Task {
    private String getData1;
    private String getData2;

    public synchronized void doLongTimeTask() {
        try{
            System.out.println("begin task");
            TimeUnit.SECONDS.sleep(3);
            getData1 = "长时间处理任务后从远程返回的值1 threadName=" + Thread.currentThread().getName();
            getData2 = "长时间处理任务后从远程返回的值2 threadName=" + Thread.currentThread().getName();
            System.out.println(getData1);
            System.out.println(getData2);
            System.out.println("end task");
        } catch (InterruptedException e){
           e.printStackTrace();
        }
    }
}
