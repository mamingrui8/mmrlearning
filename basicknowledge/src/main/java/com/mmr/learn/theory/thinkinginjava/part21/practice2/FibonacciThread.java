package com.mmr.learn.theory.thinkinginjava.part21.practice2;

/**
 * @author Charles Wesley
 * @date 2019/11/25 23:10
 */
public class FibonacciThread {
    public static void main(String[] args) {
        for(int i=1; i<5; i++){
            new Thread(new FibonacciTask(i)).start();
        }
    }
}
