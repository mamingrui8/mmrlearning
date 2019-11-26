package com.mmr.learn.theory.thinkinginjava.part21;

import java.util.concurrent.Executors;

/**
 * 使用FixedThreadPool来替代CacheThreadPool，帮助我们执行任务
 * @author Charles Wesley
 * @date 2019/11/25 23:41
 */
public class FixedThreadPool {
    public static void main(String[] args) {
        //一次性的预支高昂的线程分配开销
        /*
            好处:
            1. 一次创建，终身受益
            2. 资源不会被滥用
         */
        Executors.newFixedThreadPool(5);
    }
}
