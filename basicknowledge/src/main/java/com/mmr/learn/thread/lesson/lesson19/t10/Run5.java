package com.mmr.learn.thread.lesson.lesson19.t10;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

@SuppressWarnings("Duplicates")
public class Run5 {
    private static Timer timer = new Timer();
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static public class MyTask extends TimerTask{
        @Override
        public void run(){
            System.out.println("开始执行，时间为: " + sdf.format(new Date()));
            System.out.println("结束执行，时间为: " + sdf.format(new Date()));
            System.out.println();
        }
    }

    public static void main(String[] args) {
        MyTask task = new MyTask();
        try {

            Date dateRef = sdf.parse("2019-03-14 23:25:00");
            System.out.println("字符串时间：" + dateRef.toLocaleString() +  " 当前时间 " + sdf.format(new Date()));

            timer.schedule(task, dateRef, 5000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
