package com.mmr.learn.theory.thinkinginjava.part21.practice2;

import com.mmr.learn.theory.thinkinginjava.part15.Fibonacci;

import java.util.Arrays;

/**
 * @author Charles Wesley
 * @date 2019/11/25 23:04
 */
public class FibonacciTask implements Runnable{
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
    public void run() {
        Integer[] sequence = new Integer[n];
        for(int i=0; i<n; i++){
            sequence[i] = fibonacci.next();
        }
        System.out.println("id: " + id + " --- " + Arrays.toString(sequence));
    }
}
