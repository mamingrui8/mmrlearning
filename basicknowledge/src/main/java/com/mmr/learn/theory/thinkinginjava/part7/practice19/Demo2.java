package com.mmr.learn.theory.thinkinginjava.part7.practice19;

public class Demo2 {
    private final Demo1 demo1;

    public Demo2(){
        demo1 = new Demo1();
    }

    public static void main(String[] args) {
        Demo2 demo2 = new Demo2();
        demo2.demo1.test2();

        // demo2.demo1 = null;
    }

    final int test(){
        return 10;
    }
}
