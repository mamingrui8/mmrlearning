package com.mmr.learn.theory.thinkinginjava.part13;

public class ArrayListDisplay {
    public static void main(String[] args) {
        String s = "123";
        String a = s;
        String b = s.intern();
        b = "456";
        System.out.println("a ->" + a);
        System.out.println("b ->" + b);

        System.out.printf("Row1 : [%d]", 1000);
    }
}
