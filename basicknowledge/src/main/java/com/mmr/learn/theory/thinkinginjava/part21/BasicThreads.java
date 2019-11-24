package com.mmr.learn.theory.thinkinginjava.part21;

/**
 * 将Runnable对象转变为工作任务
 * 本类介绍如何使用Thread来驱动LifeOff对象
 * @author Charles Wesley
 * @date 2019/11/24 22:37
 */
public class BasicThreads {
    public static void main(String[] args) {
        Thread thread = new Thread(new LifeOff());
        thread.start();
        System.out.println("Waiting for LifeOff");
    }
}
