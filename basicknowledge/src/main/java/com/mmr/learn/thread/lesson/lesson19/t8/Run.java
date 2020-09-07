package com.mmr.learn.thread.lesson.lesson19.t8;

import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Run {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    static public class MyTask extends TimerTask{
        @Override
        public void run() {
            try {
                System.out.println("运行了！当前时间为: " + sdf.format(new Date()));
                TimeUnit.SECONDS.sleep(7);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String []args){
        MyTask task = new MyTask();
        Timer timer = new Timer();
        System.out.println(" 当前时间: " + sdf.format(new Date()));
        timer.schedule(task, 3000, 5000);
    }
}
