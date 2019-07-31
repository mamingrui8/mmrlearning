package com.mmr.learn.theory.thinkinginjava.part7.practice13;

public class Main {
    public void test(String name, int age){
        System.out.println("test1");
    }

    public void test(String name){
        System.out.println("test2");
    }

    public void test(int age){
        System.out.println("test3");
    }
}

class SubObj extends Main{
    public void test(){
        System.out.println("test4");
    }

    public static void main(String[] args) {
        SubObj obj = new SubObj();
        obj.test("小马", 25);
        obj.test("小马");
        obj.test(25);
        obj.test();
    }
}


