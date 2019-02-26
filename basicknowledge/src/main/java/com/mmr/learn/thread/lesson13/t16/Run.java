package com.mmr.learn.thread.lesson13.t16;

import java.util.concurrent.TimeUnit;

/**
 *
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月19日 10:34
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class Run {
    public static void main(String[] args){
        try {
            RunThread runThread = new RunThread();
            runThread.start();
            TimeUnit.SECONDS.sleep(1);
            runThread.setRunning(false);
            System.out.println("已经赋值为false");
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    /**
     * 本例出现死循环
     * 分析原因:
     * 在启动RunThread.java线程时，变量private boolean isRunning = true存在于公共堆及线程的私有本地方法栈中。
     * 为了线程运行效率(毕竟栈的速度比堆快多了)，线程一直在私有方法栈中取得isRunning的值是true，
     * 而代码thread.setRunning(false);虽然被执行，但更新的却是公共堆中的isRunning变量值false。
     * 所以一直处于死循环的状态。
     */
}
