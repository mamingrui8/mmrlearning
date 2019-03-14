package com.mmr.learn.thread.lesson19.t6;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Run {
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static Timer timer = new Timer();

    static public class MyTaskA extends TimerTask{
        public void run(){
            System.out.println("A运行了！ 时间为: " + sdf.format(new Date()));
            timer.cancel();
        }
    }

    static public class MyTaskB extends TimerTask{
        public void run(){
            System.out.println("B运行了！ 时间为: " + sdf.format(new Date()));
        }
    }

    public static void main(String []args){
        MyTaskA taskA = new MyTaskA();
        MyTaskB taskB = new MyTaskB();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        System.out.println("当前时间为: " + sdf.format(new Date()));

        calendar.add(Calendar.SECOND, 4);
        timer.schedule(taskA, calendar.getTime(), 4000);
        timer.schedule(taskB, calendar.getTime(), 4000);
    }
}
