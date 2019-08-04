package com.mmr.learn.theory.thinkinginjava.part10.practice5;

public class OtherObj {
    public static void main(String[] args) {
        //创建另一个类中的内部类实例

        //Step1: 创建外部类的实例
        Outer outer = new Outer();
        //Step2: 创建外部类内的内部类实例
        Outer.Inner inner = outer.new Inner();
    }
}
