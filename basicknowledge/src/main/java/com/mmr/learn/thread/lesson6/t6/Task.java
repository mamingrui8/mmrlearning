package com.mmr.learn.thread.lesson6.t6;

import java.util.concurrent.TimeUnit;

/**
 * Description: TODO
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
            String privateGetData1 = "长时间处理任务后从远程返回的值1 threadName=" + Thread.currentThread().getName();
            String privateGetData2 = "长时间处理任务后从远程返回的值2 threadName=" + Thread.currentThread().getName();
            synchronized(this){ //把长时间处理的任务从同步代码块中分离出来
                getData1 = privateGetData1;
                getData2 = privateGetData2;
            }
            System.out.println(getData1);
            System.out.println(getData2);
            System.out.println("end task");
        } catch (InterruptedException e){
           e.printStackTrace();
        }
    }
}
