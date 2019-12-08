package com.mmr.learn.theory.thinkinginjava.part21;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * 如ExceptionThread可知，在线程启动处(线程与任务附着)捕获异常是徒劳的
 * 那么我们该如何捕获这些从run()中逃逸的异常呢？本例给出了解释！
 *
 * 可以看到，在run()中未捕获的异常是通过Thread.UncaughtExceptionHandler来捕获的
 * @author Charles Wesley
 * @date 2019/12/8 18:37
 */
public class CaptureUncaughtException{
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool(new HandlerThreadFactory());
        exec.execute(new ExceptionThread2());
    }
}

class ExceptionThread2 implements Runnable{
    @Override
    public void run() {
        Thread t = Thread.currentThread();
        //当前任务(Task)是由哪一个线程驱动(运行)
        System.out.println("run() by " + t);
        System.out.println("eh= " + t.getUncaughtExceptionHandler());
        throw new RuntimeException();
    }
}

class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("caught " + e);
    }
}

class HandlerThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(@NotNull Runnable r) {
        System.out.println(this + " creating new Thread");
        //main线程创建出了一个新线程
        Thread t = new Thread(r);
        System.out.println("created " + t);
        t.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        System.out.println("eh= " + t.getUncaughtExceptionHandler());
        return t;
    }
}
