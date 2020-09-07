package com.mmr.learn.thread.lesson.lesson19.t9;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Description: 测试schedule()方法 任务延时结束
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年03月14日 14:49
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */

@SuppressWarnings("Duplicates")
public class t9_2_run {
    private static Timer timer = new Timer();
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static int runCount = 0;

    static public class MyTask1 extends TimerTask {
        @Override
        public void run() {
            try {
                System.out.println("begin 开始运行！时间为: " + sdf.format(new Date()));
                TimeUnit.SECONDS.sleep(10);
                System.out.println("begin 结束运行！时间为: " + sdf.format(new Date()));
                runCount++;
                if(runCount == 5){
                    timer.cancel();
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    /**
     *  任务的执行时长: 10秒左右
     *  任务的执行周期: 3秒
     *  延时5秒开始第一次执行
     */
    public static void main(String []args){
        MyTask1 task1 = new MyTask1();
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, 5);

        System.out.println("main线程，当前时间为: " + sdf.format(date));

        timer.schedule(task1, calendar.getTime(), 3000);
    }
}
