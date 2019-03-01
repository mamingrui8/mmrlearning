package com.mmr.learn.thread.lesson16.t4;

import java.util.concurrent.TimeUnit;

public class ThreadB extends Thread{
    @Override
    synchronized public void run(){ //非常特殊！ 居然在run()上加上了同步锁！
        try {
            System.out.println("begin B ThreadName=" + Thread.currentThread().getName() + "  " + System.currentTimeMillis());
            TimeUnit.SECONDS.sleep(5);
            System.out.println("end B ThreadName=" + Thread.currentThread().getName() + "  " + System.currentTimeMillis());
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
