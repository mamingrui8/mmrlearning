package com.mmr.learn.theory.thinkinginjava.part15.practice10;

public class GenericMethods {
    public <T, U, K> void f(T x, U y, K z, String ss){
        System.out.println("x: " + x.getClass().getName());
        System.out.println("y: " + y.getClass().getName());
        System.out.println("z: " + z.getClass().getName());
        System.out.println(ss);
    }

    public static void main(String[] args) {
        GenericMethods gm = new GenericMethods();
        gm.f('c', 5, 1.0, "你好");
    }
}