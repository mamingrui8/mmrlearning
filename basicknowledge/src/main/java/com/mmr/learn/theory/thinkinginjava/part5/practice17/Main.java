package com.mmr.learn.theory.thinkinginjava.part5.practice17;

public class Main {
    Main(String name){
        System.out.println(name);
    }

    public static void main(String[] args) {
        Main[] mm = new Main[1];
        mm[0] = new Main(123 + "");

    }

    static void printArray(Integer... args){
        System.out.println( );
    }
}
