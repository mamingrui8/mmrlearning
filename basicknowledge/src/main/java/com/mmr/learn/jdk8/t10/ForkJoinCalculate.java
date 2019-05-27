package com.mmr.learn.jdk8.t10;

import java.util.concurrent.RecursiveTask;

/**
 * jdk1.7中使用Fork/Join框架
 *
 * 需要定义:
 * 1. 如何拆分
 * 2. 拆到什么程度为止
 */
public class ForkJoinCalculate extends RecursiveTask<Long> {
    private static final long serialVersionUID = 1212853982210556381L;

    private long start;
    private long end;
    //拆分的阈值，任务的最小单位是100000，拆到这个程度就不用再拆了
    private static final long THRESHOLD = 100000;

    public ForkJoinCalculate(long start, long end){
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long length = start - end;
        if(length <= THRESHOLD){ //进行求和
            long sum  = 0;
            for(long i = start; i <= end; i++){
                sum += i;
            }
            return sum;
        }else{ //进行拆分
            long middle = (start + end) / 2;

            ForkJoinCalculate left = new ForkJoinCalculate(start, middle);
            left.fork(); //拆分子任务，并将任务压入线程队列

            ForkJoinCalculate right = new ForkJoinCalculate(middle + 1, end);
            right.fork();

            //结果汇总
            return left.join() + right.join();
        }
    }
}
