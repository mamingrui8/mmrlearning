package com.mmr.learn.theory.thinkinginjava.part15.practice7;

import com.mmr.learn.theory.thinkinginjava.part15.Fibonacci;

import java.util.Iterator;

/**
 * 使用组合而不是继承的方式 使Fibonacci成为Iterable
 * @author Charles Wesley
 * @date 2019/11/19 23:08
 */
public class IterableFibonacci implements Iterable<Integer> {
    private Fibonacci fibonacci = new Fibonacci();
    /**
     * 数列的最大长度 实际上就是一个末端哨兵
     */
    private int n;

    public IterableFibonacci(int count){
        n = count;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            @Override
            public boolean hasNext() {
                return n>0;
            }

            @Override
            public Integer next() {
                --n;
                return fibonacci.next();
            }
            @Override
            public void remove(){
                throw new UnsupportedOperationException();
            }
        };
    }

    public static void main(String[] args) {
        for(int n : new IterableFibonacci(5)){
            System.out.print(n + " ");
        }
    }
}
