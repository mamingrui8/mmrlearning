package com.mmr.learn.theory.thinkinginjava.part15;

import net.mindview.util.Generator;

/**
 * 实现Fibonacci数列
 * @author Charles Wesley
 * @date 2019/11/17 22:24
 */
public class Fibonacci implements Generator<Integer> {
    private int count = 0;

    @Override
    public Integer next() {
        return fib(count++);
    }
    /**
     * 获取指定下标的元素
     */
    private int fib(int n){
        if(n < 2){
            return 1;
        }else{
            return fib(n-2) + fib(n-1);
        }
    }

    public static void main(String[] args) {
        Fibonacci gen = new Fibonacci();
        for(int i=0; i< 18; i++){
            System.out.print(gen.next() + " ");
        }
    }
}

