package com.mmr.learn.theory.thinkinginjava.part7.practice16;

public class Frog extends Amphibian{
    @Override
    public void test1(){
        System.out.println("323232323");
    }
    public static void main(String[] args) {
        Frog frog = new Frog();
        frog.test1();
        frog.test2(frog);
    }
}
