package com.mmr.learn.thread.lesson19.t4;


import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Run {
    static public class MyTask extends TimerTask {
        @Override
        public void run(){
            System.out.println("运行了！ 当前的时间为: " + System.currentTimeMillis());
        }
    }

    public static void main(String[] args) {
        Date date = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, 10);

        Timer timer = new Timer();
        System.out.println("main线程指定时间为: " + System.currentTimeMillis());
        MyTask task = new MyTask();
        timer.schedule(task, calendar.getTime(), 4000);
    }
}
