package com.mmr.learn.theory.thinkinginjava.part5.practice2;

public class Test {
    private String name = "小明";

    Test(){
        name = "小红";
    }

    public static void main(String[] args) {
        Test test = new Test();
        System.out.println(test.name);
    }
}
