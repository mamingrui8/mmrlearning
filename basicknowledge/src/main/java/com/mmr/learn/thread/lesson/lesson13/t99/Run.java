package com.mmr.learn.thread.lesson.lesson13.t99;

/**
 *
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月19日 10:08
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class Run {
    public static void main(String[] args){
        PrintString printStringService = new PrintString();
        printStringService.printStringMethod();
        System.out.println(" 我要停止它！ stopThread=" + Thread.currentThread().getName());
        printStringService.setContinuePrint(false);
    }

    /**
     * 结果根本不可能停下来，因为本例中只有一个线程，那就是main线程。
     * 由于main线程在printStringMethod()方法内被阻塞了，
     * 因此printStringService.setContinuePrint(false);得不到执行
     * 从而无法停止线程
     *
     * 解决办法: 使用多线程技术
     *
     */
}
