package com.mmr.learn.theory.thinkinginjava.part21;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 使用Executor来代替在MoreBasicThread中显示地创建Thread对象。
 * 设计模式: 命令模式
 * @author Charles Wesley
 * @date 2019/11/25 23:28
 */
public class CacheThreadPool {
    public static void main(String[] args) {
        //ExecutorService对象是使用Executors的静态方法来创建的，这个方法可以确定Executor的类型
        //Executor充当了"中介对象"来帮助我们执行任务
        //当前的场景下，单个的Executor被用来创建和管理系统中的所有任务
        ExecutorService exec = Executors.newCachedThreadPool();
        for(int i = 0; i < 5; i++){
            exec.execute(new LifeOff());
        }
        //调用Executor的shutdown()方法后，Executor将不再接收新的任务，当前线程(在本例中，即驱动main()的线程)将继续运行在shutdown()被调用前提交的所有任务。
        //这个程序将在Executor中的所有任务完成后尽快退出(并非是立刻退出！)
        exec.shutdown();

        //下方的任务不会被线程调度器分配线程去执行，因为已经执行了ExecutorService.shutdown()
        //如果把下方代码的注释打开，则会报错: java.util.concurrent.RejectedExecutionException
//        for(int i = 0; i < 5; i++){
//            exec.execute(new LifeOff());
//        }

        /*
            选择CachedThreadPool的好处在于:
            CachedThreadPool在程序的执行过程中，会自动创建出于所需数量相同的线程，然后在线程池回收旧线程时，停止创建新线程。
            因此，CachedThreadPool是合理的Executor的首选。只有当CachedThreadPool会出现问题时，我们才需要切换到FixedThreadPool
         */
    }
}
