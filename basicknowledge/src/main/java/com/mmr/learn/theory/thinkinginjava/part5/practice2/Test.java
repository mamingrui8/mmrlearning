package com.mmr.learn.theory.thinkinginjava.part5.practice2;

public class Test {
    private String name = "小明";

    private int age;

    Test(){
        age = 24;
    }

    public static void main(String[] args) {
        Test test = new Test();
        System.out.println(test.name);
        System.out.println(test.age);
    }
}
