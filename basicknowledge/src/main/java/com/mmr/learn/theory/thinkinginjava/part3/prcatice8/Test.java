package com.mmr.learn.theory.thinkinginjava.part3.prcatice8;

public class Test {
    public static void main(String[] args) {
        long num1 = 0123;
        long num2 = 0x123;

        System.out.println(num1);
        System.out.println(num2);
        System.out.println(Long.toBinaryString(num1));
        System.out.println(Long.toBinaryString(num2));
    }
}
