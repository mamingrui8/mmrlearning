package com.mmr.learn.thread.lesson.lesson13.t10;

public class Run {
    public static void main(String[] args){
        PrintString printStringService = new PrintString();
        new Thread(printStringService).start();
        System.out.println(" 我要停止它！ stopThread=" + Thread.currentThread().getName());
        printStringService.setContinuePrint(false);
    }

    /**
     * printStringService作为子线程单独运行的好处在于:
     * 调用printStringMethod()后，子线程暂停，main线程得以执行。
     */
}
