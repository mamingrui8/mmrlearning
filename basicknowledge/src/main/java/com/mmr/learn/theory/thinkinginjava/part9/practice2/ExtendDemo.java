package com.mmr.learn.theory.thinkinginjava.part9.practice2;

/**
 * 不能为抽象类创建任何的实例，哪怕抽象类中没有定义任何的抽象方法
 */
public class ExtendDemo {
    public static void main(String[] args) {
        //此句会报错: Demo is abstract,cannot be initialized.
        //Demo demo = new Demo();
    }
}
