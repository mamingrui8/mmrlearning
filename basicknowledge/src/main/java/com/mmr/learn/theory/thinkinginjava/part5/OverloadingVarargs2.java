package com.mmr.learn.theory.thinkinginjava.part5;

public class OverloadingVarargs2 {
    static void f(float i, Character... args){
        System.out.println("first");
    }

    static void f(char c, Character... args){
        System.out.println("second");
    }

    public static void main(String[] args) {
        f(1, 'a');
        f('a', 'c');

        float fff = 'c';

        System.out.println(fff);
    }
}
