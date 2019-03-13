package com.mmr.learn.thread.lesson19.t4;

import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Run2 {
    static public class MyTask extends TimerTask{
        public void run(){
            try {
                System.out.println(Thread.currentThread().getName() + " 开始运行，当前的时间为: " + System.currentTimeMillis());
                TimeUnit.SECONDS.sleep(10);
                System.out.println(Thread.currentThread().getName() + " 结束运行，当前的时间为: " + System.currentTimeMillis());
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        MyTask task = new MyTask();
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //TODO
    }
}
