package com.mmr.learn.theory.thinkinginjava.part5.practice3;

public class Test {
    Test(){
        System.out.println("Constract invoke");
    }

    Test(String name){
        System.out.println(name);
    }

    public void barking(String name, Integer age){
        System.out.println(name + "-" + age);
    }

    public void barking(Integer age, String name){
        System.out.println(name + "-" + age);
    }
}
