package com.mmr.learn.thread.lesson19.t7;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Run {
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    static public class MyTask extends TimerTask{
        @Override
        public void run() {
            System.out.println("A运行了！ 时间为: " + sdf.format(new Date()));
        }
    }

    public static void main(String []args){
        MyTask task = new MyTask();
        Timer timer = new Timer();
        System.out.println("当前时间: " + sdf.format(new Date()));
        timer.schedule(task, 5000);
    }
}
