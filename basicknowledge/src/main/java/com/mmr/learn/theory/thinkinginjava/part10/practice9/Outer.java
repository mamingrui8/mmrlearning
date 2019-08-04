package com.mmr.learn.theory.thinkinginjava.part10.practice9;

import java.util.Random;

public class Outer {
    public Inter tt(){
        class Inner implements Inter{
            @Override
            public void test() {

            }
        }
        return new Inner();
    }

    private class Inter2 implements Inter{
        @Override
        public void test() {
            System.out.println("test()");
        }
    }

    public Inter t3(){
        if(0 < new Random(10).nextInt(20)){
            class ttt{

            }
        }
        return new Inter2();
    }

    public static void main(String[] args) {
        Outer outer = new Outer();
        Inter inter = outer.t3();
        Inter2 inter2 = (Inter2)inter;
        inter.test();
    }
}
