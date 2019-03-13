package com.mmr.learn.thread.lesson19.t2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Run {
    public static void main(String []args){
        MyTask task = new MyTask();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = "2019-03-13 17:39:30";
        Timer timer = new Timer();
        System.out.println("main当前时间: " + new Date());
        try {
            timer.schedule(task, sdf.parse(dateString));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    static public class MyTask extends TimerTask {
        @Override
        public void run() {
            System.out.println("运行了！ 当前的时间为: " + new Date());
        }
    }
}
