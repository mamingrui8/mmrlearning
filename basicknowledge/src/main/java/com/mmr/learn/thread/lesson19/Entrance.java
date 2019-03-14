package com.mmr.learn.thread.lesson19;

import java.util.concurrent.TimeUnit;

/**
 * 定时器
 * t9比较难 务必仔细思考
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
     *              1. 任务仍然会按照填装顺序执行
     *              2. 如果任务执行的时间间隔小于任务本身的执行时间，那么每当上一次任务执行完毕，下一次任务的执行就会接踵而来。
     *
     *   t5 TimerTask类的cancel()
     *      概念: 将自身从任务队列中清除
     *      执行结果:
     *           当前时间为: 2019-03-14 10:08:51
     *           A运行了！ 时间为: 2019-03-14 10:08:55
     *           B运行了！ 时间为: 2019-03-14 10:08:55
     *           B运行了！ 时间为: 2019-03-14 10:08:59
     *           B运行了！ 时间为: 2019-03-14 10:09:03
     *      思考:
     *           观察发现，A仅在运行一次后就被清除了，这是因为A调用了TimerTask.cancel();
     *
     *   t6 Timer类的cancel()
     *      概念: 将任务队列中的所有任务全部清除
     *      思考: 由于cancel()方法中会将newTasksMayBeScheduled置为false, 这会导致Timer内部线程的run()方法中的while循环break,
     *            既然run()执行完毕，那么Timer内部线程也会随之停止，由于Task线程依赖Timer线程来启动，因此Task线程也结束。
     *            所有线程结束，整个进程终止。
     *
     *      Timer类的cancel()并不一定会停止继续执行任务
     *      思考: 刚刚t6不是表明被cancel()后，任务会被清除吗，为什么这里又说cancel()不一定会停止任务的继续周期执行呢？
     *            答案很简单，请看cancel()的源码
     *            public void cancel() {
     *                  synchronized(queue) {
     *                      thread.newTasksMayBeScheduled = false;
     *                      queue.clear();
     *                      queue.notify();  // 如果有漏网之鱼，让其任务执行了事。
     *                  }
     *            }
     *            在进行清除操作之前，Timer必须拿到queue对象监视器的锁，如果争抢不到queue锁就执行不了清除代码并一直处于等待状态。
     *            Task任务继续正常执行很好理解，但重复的执行就让人感到不解了:
     *            //TODO Timer内部到底有几条线程？为什么Timer线程处于等待状态(执行cancel()并 等待queue锁)，Task仍然能被定时执行？换句话说，Task到底是如何被执行的？
     *
     *    t7 schedule(TimeTask task, long delay)
     *       概念: 在当前时间的基础上，向后延时指定的毫秒数后执行一次TimeTask任务
     *
     *    t8 schedule(TimerTask task, long delay, long period)
     *       概念: 在当前时间的基础上，向后延时指定的毫秒数后执行一次TImeTask任务，后续每隔period毫秒数重复执行任务
     *       思考:
     *             情况1: 任务的运行时间 < 时间间隔
     *                        当前时间: 2019-03-14 14:25:04
     *                        运行了！当前时间为: 2019-03-14 14:25:07
     *                        运行了！当前时间为: 2019-03-14 14:25:12
     *             情况2: 任务的运行时间 > 时间间隔
     *                        当前时间: 2019-03-14 14:27:18
     *                        运行了！当前时间为: 2019-03-14 14:27:21
     *                        运行了！当前时间为: 2019-03-14 14:27:28
     *                        运行了！当前时间为: 2019-03-14 14:27:35
     *                    即前一个任务执行完毕后，下一个任务立刻接踵而来。
     *
     *
     *     t9 scheduleAtFixedRate
     *        概念: 如果执行任务的时间没有被延时，那么下一次任务的执行时间参考的是上一次任务"结束"的时间来计算。
     *              如果执行任务的时间被延时了，那么下一次任务的执行时间参考的是上一次任务"结束"的时间来计算。
     *
     *    所谓的"不延时"，指的是任务的计划执行时间没有被拖延
     *        1. t9_1_run 测试schedule任务不延时，  任务的执行时间 < 时间间隔
     *           效果: 任务的本次执行时间 = 上一次任务开始执行的时间 + 时间间隔
     *        2. t9_2_run 测试schedule任务不延时，  任务的执行时间 > 时间间隔
     *           效果: 任务的本次执行时间 = 上一下任务结束执行的时间 + 时间间隔
     *           说明: 任务原本就已经推迟结束了，为了减小损失，当然应该没有丝毫的犹豫，立刻进入下一次执行过程。
     *        3. t9_3_run 测试scheduleAtFixedRate方法任务不延时
     *           效果:
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
