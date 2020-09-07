package com.mmr.learn.thread.lesson.lesson19.t9;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Description: 测试scheduleAtFixedRate() 任务不延时结束
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年03月14日 17:58
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
@SuppressWarnings("Duplicates")
public class t9_3_run {
    private static Timer timer = new Timer();
    private static int runCount = 0;
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static public class MyTask1 extends TimerTask {
        @Override
        public void run() {
            try {
                System.out.println("begin 开始运行！时间为: " + sdf.format(new Date()));
                TimeUnit.SECONDS.sleep(2);
                System.out.println("begin 结束运行！时间为: " + sdf.format(new Date()));
                System.out.println("");
                runCount++;
                if(runCount == 5){
                    timer.cancel();
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        MyTask1 task1 = new MyTask1();
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, 5);

        System.out.println("main线程，当前时间为: " + sdf.format(date));

        timer.scheduleAtFixedRate(task1, calendar.getTime(), 4000);
    }
}
