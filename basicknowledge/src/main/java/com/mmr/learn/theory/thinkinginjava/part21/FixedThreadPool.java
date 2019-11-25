package com.mmr.learn.theory.thinkinginjava.part21;

import java.util.concurrent.Executors;

/**
 * 使用FixedThreadPool来替代CacheThreadPool，帮助我们执行任务
 * @author Charles Wesley
 * @date 2019/11/25 23:41
 */
public class FixedThreadPool {
    public static void main(String[] args) {
        Executors.newFixedThreadPool(5);
    }
}
