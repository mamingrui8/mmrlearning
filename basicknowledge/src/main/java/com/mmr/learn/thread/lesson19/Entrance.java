package com.mmr.learn.thread.lesson19;

import java.util.concurrent.TimeUnit;

/**
 * 定时器
 */
public class Entrance {
    /**
     * Timer主要负责计划任务的功能，也即在指定的时间开始执行某一个任务。
     * Timer类的主要任务是设置计划任务，但封装任务的类却是TimeTask.
     *
     * t1 schedule(TimeTask task, Date time)
     *    概念: 在指定的时间执行一次某任务
     *    执行结果:
     *          main方法，当前的时间为: Wed Mar 13 16:30:12 CST 2019
     *          运行了！ 当前的时间为: Wed Mar 13 16:30:22 CST 2019
     *    思考:
     *          1. 任务虽然执行完了，但进程仍未销毁，查看源码发现，new Timer()实际上启动了一个线程
     *             这里有三个线程，分别是main线程、timer线程以及MyTask线程。其中，main线程最先执行完毕，MyTask线程执行完毕后，Timer线程仍然在运行。
     *             但如果把Timer设置成Daemon(守护线程)，则main线程执行完毕后会迅速结束当前线程，并且TimeTask中的任务也不再被运行，因为进程已经结束了。
     *
     *             不是守护线程，任务可以正常执行。 是守护线程，任务反而不能执行
     *  t2 schedule(TimeTask task, Date time)  若传入的time早于当前时间，则立刻执行task任务。
     *     执行结果:
     *          main当前时间: Wed Mar 13 17:41:29 CST 2019
     *          运行了！ 当前的时间为: Wed Mar 13 17:41:29 CST 2019
     *  t3 多个TimerTask任务
     *     执行结果:
     *             main线程时间1: 1552470686458
     *             main线程时间2: 1552470686458
     *             MyTask1运行了，当前时间是: 1552470689422 ------ 实际启动timer3秒后开始执行
     *             MyTask2运行了，当前时间是: 1552470699421 ------ 实际启动timer13秒后开始执行    (已经很明显了， MyTask2必须等 MyTask1执行完毕后才能执行)
     *     概念: 由于Timer中用TaskQueue来存储TimeTask，当然也就支持多个任务。
     *     思考: TimerTask是以队列的方式依照加入queue的顺序来执行任务，所以执行的时间有可能和预期时间不一致，因为前面的任务可能消耗的时间较长，则后面的任务运行的时间也会被延迟。
     *
     *  t4 schedule(TimeTask task, Date firstTime, long period) 测试
     *     概念: 在指定的日期之后，按照指定的间隔周期性的无限循环执行某一任务
     *           这里会有三种情况:
     *           1. 任务的计划执行时间晚于当前时间
     *              等待计划执行时间的来临并第一次执行任务，此后每隔period(单位: 毫秒)无限期的重复执行
     *           2. 任务的计划执行时间早于当前时间
     *              立刻执行计划，并且每隔period(单位: 毫秒)无限期的重复执行
     *           3. 任务的执行时间延时 —— Run2.java
     *
     *
     */
    public static void main(String[] args){
        Thread thread = new Thread(){
            @Override
            public void run(){
                try {
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println("你好");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.setDaemon(true);
        thread.start();
        System.out.println("main线程执行完毕");
    }
}
