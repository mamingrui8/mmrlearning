package com.mmr.learn.thread.lesson19.t3;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Date;

public class Run {
    public static void main(String []args){
        MyTask1 task1 = new MyTask1();
        MyTask2 task2 = new MyTask2();

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        System.out.println("main线程时间1: " + System.currentTimeMillis());
        System.out.println("main线程时间2: " + System.currentTimeMillis());

        Timer timer = new Timer();
        calendar.add(Calendar.SECOND, 3);
        timer.schedule(task1, calendar.getTime());

        calendar.add(Calendar.SECOND, 10);
        timer.schedule(task2, calendar.getTime());
    }

    static public class MyTask1 extends TimerTask{
        @Override
        public void run(){
            System.out.println("MyTask1运行了，当前时间是: " + System.currentTimeMillis());
        }
    }

    static public class MyTask2 extends TimerTask{
        @Override
        public void run(){
            System.out.println("MyTask2运行了，当前时间是: " + System.currentTimeMillis());
        }
    }
}

