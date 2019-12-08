package com.mmr.learn.theory.thinkinginjava.part21;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 偶数检查
 *
 * 疑问:
 * 1. IntGenerator generator是怎样成为共享变量的？明明不是静态变量。
 *    答: 虽然看起来创建了多个EvenChecker对象，但实际上初始化时使用的是同一个IntGenerator对象！
 * @author Charles Wesley
 * @date 2019/12/8 20:36
 */
public class EvenChecker implements Runnable{
    private IntGenerator generator;
    private final int id;
    public EvenChecker(IntGenerator g, int ident) {
        generator  =g;
        id = ident;
    }
    @Override
    public void run() {
        while(!generator.isCanceled()) {
            int val = generator.next();
            if(val % 2 != 0) {
                System.out.println(val + "not even!");
                generator.cancel();
            }
        }
    }
    //Test any type of IntGenerator
    public static void test(IntGenerator gp, int count) {
        System.out.println("Press Control-C to exit");
        ExecutorService exec = Executors.newCachedThreadPool();
        for(int i = 0; i < count; i++) {
            //无论创建多少次EvenChecker，使用的IntGenerator都是同一个！这就导致IntGenerator在多个线程驱动的多个任务中共享！
            exec.execute(new EvenChecker(gp, i));
        }
        exec.shutdown();
    }
    //Default value for count:
    public static void test(IntGenerator gp) {
        test(gp, 10);
    }
}
