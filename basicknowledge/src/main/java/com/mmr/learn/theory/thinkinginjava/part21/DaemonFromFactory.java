package com.mmr.learn.theory.thinkinginjava.part21;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 通过ThreadFactory来初始化的Executors
 * @author Charles Wesley
 * @date 2019/11/27 23:47
 */
public class DaemonFromFactory implements Runnable{
    @Override
    public void run() {
        try {
            while(true){
                TimeUnit.MILLISECONDS.sleep(100);
                System.out.println(Thread.currentThread() + " " + this);
            }
        }catch (InterruptedException e){
            System.out.println("Thread interrupted");
        }
    }

    public static void main(String[] args) throws Exception{
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
        ExecutorService es =  Executors.newCachedThreadPool(new DaemonThreadFactory());
        for(int i = 0; i < 10; i++){
            es.execute(new DaemonFromFactory());
        }
        System.out.println("All daemons started");
        TimeUnit.MILLISECONDS.sleep(500);
        System.out.println(123);
    }

    /*
        Step1: main线程(非后台线程)输出"All daemons started"，遇到sleep()命令进入休眠，休眠时间500ms
        Step2: 一个后台线程(可能是10个当中的任意一个)开始执行run()方法，当遇到sleep命令后，立刻通知调度器，让出CPU供其余的后台线程执行
               如果把线程调度器切换线程的时间忽略不计，那么所有的后台线程都进入了休眠状态，休眠时间100ms
        Step3: 100ms结束后，每一个后台线程都会输出Thread.currentThread() + " " + this 语句。
        Step4: 重复Step2和3总共4轮。当到第5轮，也就是所有的线程(包括main线程)全部度过了500ms后，main线程开始执行，并执行结束。
               由于场上唯一的一个非后台线程结束运行，因此java进程将会被杀死，后台线程也会随之被杀死。
        总计输出 1+ 10 * 4 + 1 = 42条语句

        疑问: 500毫秒时，所有的线程都被唤醒并进入"可运行态"了，凭什么main线程(非后台线程/用户线程)先执行呢？难道说用户线程的优先级比守护线程的优先级高吗？
        分析: 为了验证这个问题，我在DaemonThreadFactory中人为的把守护线程的优先级设置成了最高，在main中把main线程的优先级设置成最低，看看结果如何。
              结果发现，守护线程仍然不会先于用户线程执行，所以和线程的优先级没什么关系。
     */
}
