package com.mmr.learn.thread.lesson19.t1;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Date;

public class Run {
    /*
        当初始化对象时，就创建了线程。此线程会进入mainLoop()方法内的
        while (queue.isEmpty() && newTasksMayBeScheduled) queue.wait();
        此时队列毋庸置疑是空的，而newTasksMayBeScheduled在初始状态是true，因此进入等待状态
        既然有wait()，那就一定有notify()，让我们来看看notify()在哪

        if (queue.getMin() == task)
        queue.notify();

        如果队列的首部和当前的任务是同一个任务，那么将队列唤醒

     */
    private static Timer timer = new Timer();

    static public class MyTask extends TimerTask {
        @Override
        public void run() {
            System.out.println("运行了！ 当前的时间为: " + new Date());
            //timer.cancel(); //调用timer.cancel()并没有使线程立刻停止
        }
    }
    public static void main(String []args) throws InterruptedException {
        MyTask task = new MyTask();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND, 5);

        System.out.println("main方法，当前的时间为: " + new Date());

        timer.schedule(task, calendar.getTime());

        //TimeUnit.SECONDS.sleep(15);
    }
}
