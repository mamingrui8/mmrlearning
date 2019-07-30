package com.mmr.learn.theory.thinkinginjava.part5.practice20;

public class Main {
    public static void main(String[] args) {
        T1.main("1", "李");
    }
}

class T1{
    public static void main(String... args) {
        for(String s : args){
            System.out.println(s);
        }
    }
}
