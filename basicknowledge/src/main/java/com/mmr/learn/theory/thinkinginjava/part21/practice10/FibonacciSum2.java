package com.mmr.learn.theory.thinkinginjava.part21.practice10;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Charles Wesley
 * @date 2019/12/7 23:12
 */
public class FibonacciSum2 {
    private static ExecutorService exec;

    private static Integer fib(int n) {
        if (n < 2) {
            return 1;
        }else {
            return fib(n-2) + fib(n-1);
        }
    }

    public static synchronized Future<Integer> runTask(final int n) {
        assert exec!=null;
        return exec.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int sum = 0;
                for (int i = 0; i < n; i++) {
                    sum += fib(i);
                }
                return sum;
            }
        });
    }

    public static synchronized void init() {
        if (exec ==null) {
            exec = Executors.newCachedThreadPool();
        }
    }

    public static synchronized void shutdown() {
        if(exec!=null) {
            exec.shutdown();
        }
        exec = null;
    }
}
