package com.mmr.learn.thread.lesson.lesson19.t9;

/**
 * Description: TODO
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年03月14日 23:06
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Run3 {
    private static Timer timer = new Timer();
    private static int runCount = 0;
    static public class MyTask1 extends TimerTask{
        @Override
        public void run(){
            try {
                System.out.println("1 begin 运行了！ 时间为: " + new Date());
                Thread.sleep(2000);
                System.out.println("1 end 运行了！ 时间为: " + new Date());
                runCount ++;
                if(runCount == 5){
                    timer.cancel();
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            MyTask1 task1 = new MyTask1();
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = "2019-03-14 23:13:30";
            Date dateRef = sdf1.parse(dateString);
            System.out.println("字符串1时间： " + new  Date().toLocaleString());
            timer.scheduleAtFixedRate(task1, dateRef, 6000);
        }catch (ParseException e){
            e.printStackTrace();
        }
    }
}
