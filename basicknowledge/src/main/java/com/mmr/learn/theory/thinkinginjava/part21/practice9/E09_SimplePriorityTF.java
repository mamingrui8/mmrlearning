package com.mmr.learn.theory.thinkinginjava.part21.practice9;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 使得定制的ThreadFactory可以设置线程的优先级
 * @author Charles Wesley
 * @date 2019/12/3 23:39
 */
public class E09_SimplePriorityTF {
    public static void main(String[] args) {
        //通过ThreadFactory实现类的构造函数，将优先级传入并赋予给每个被该工厂创建出的线程身上。
        ExecutorService exec = Executors.newCachedThreadPool(new PriorityThreadFactory(Thread.MIN_PRIORITY));
        for(int i =0; i<5; i++){
            exec.execute(new SimplePriority2());
        }
        Thread.yield();
        exec.shutdown();
//
//        exec = Executors.newCachedThreadPool(new PriorityThreadFactory(Thread.MAX_PRIORITY));
//        exec.execute(new SimplePriority2());
//        exec.shutdown();
    }
}
