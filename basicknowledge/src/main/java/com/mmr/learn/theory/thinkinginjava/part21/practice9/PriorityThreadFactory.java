package com.mmr.learn.theory.thinkinginjava.part21.practice9;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadFactory;

/**
 * 线程工厂
 * @author Charles Wesley
 * @date 2019/12/3 23:32
 */
public class PriorityThreadFactory implements ThreadFactory {
    private final int priority;
    PriorityThreadFactory(int priority){
        this.priority = priority;
    }

    @Override
    public Thread newThread(@NotNull Runnable r) {
        Thread t = new Thread(r);
        t.setPriority(priority);
        return t;
    }
}
