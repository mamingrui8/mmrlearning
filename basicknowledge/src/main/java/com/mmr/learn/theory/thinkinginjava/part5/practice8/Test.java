package com.mmr.learn.theory.thinkinginjava.part5.practice8;

public class Test {
    public void t1(){
        t2();
        this.t2();
    }

    public void t2(){
        System.out.println("t2");
    }

    public static void main(String[] args) {
        Test test = new Test();
        test.t1();
    }
}
