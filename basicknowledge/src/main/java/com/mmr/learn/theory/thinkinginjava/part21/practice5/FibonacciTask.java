package com.mmr.learn.theory.thinkinginjava.part21.practice5;

import com.mmr.learn.theory.thinkinginjava.part15.Fibonacci;

import java.util.Arrays;
import java.util.concurrent.Callable;

/**
 * @author Charles Wesley
 * @date 2019/11/27 10:22
 */
public class FibonacciTask implements Callable<String> {
    private static long counter = 0;
    private final long id = counter++;
    /**
     * 创建出由多少个Fibonacci数字组成的序列
     */
    private int n;

    private Fibonacci fibonacci = new Fibonacci();

    public FibonacciTask(int n){
        this.n = n;
    }

    @Override
    public String call() {
        Integer[] sequence = new Integer[n];
        for(int i=0; i<n; i++){
            sequence[i] = fibonacci.next();
        }
        return "id: " + id + " --- " + Arrays.toString(sequence);
    }
}
