package com.mmr.learn.theory.thinkinginjava.part21;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程的优先级
 * @author Charles Wesley
 * @date 2019/11/27 22:40
 */
public class SimplePriorities implements Runnable{
    private int countDown = 5;
    //volatile的目的是努力确保后续对d数值的运算不进行任何的编译器优化
    private volatile double d;
    private int priority;

    public SimplePriorities(int priority){
        this.priority = priority;
    }

    public String toString(){
        return Thread.currentThread() + ": " + countDown;
    }

    @Override
    public void run() {
        //设置线程的优先权
        Thread.currentThread().setPriority(priority);
        while(true){
            for(int i = 1; i < 10000; i++){
                d += (Math.PI + Math.E) / (double)i;
                if(i % 10000 == 0){
                    Thread.yield();
                }
            }
            System.out.println(this);
            if(--countDown == 0){
                return;
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        for(int i = 0; i < 5; i++){
            exec.execute(new SimplePriorities(Thread.MIN_PRIORITY));
        }
        exec.execute(new SimplePriorities(Thread.MAX_PRIORITY));
        exec.shutdown();
    }
}
