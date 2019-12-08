package com.mmr.learn.theory.thinkinginjava.part21.practice10;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author Charles Wesley
 * @date 2019/12/7 23:26
 */
public class E10_FibonacciSum2 {
    public static void main(String[] args) {
        List<Future<Integer>> results = new ArrayList<>();
        FibonacciSum2.init();
        for(int i = 1; i < 5; i++) {
            results.add(FibonacciSum2.runTask(i));
        }
        //当前main线程让出CPU资源，让上方的线程充分执行
        Thread.yield();
        //线程池不再执行(接纳)其它任何任务
        FibonacciSum2.shutdown();
        for (Future<Integer> future : results) {
            try {
                System.out.println(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
