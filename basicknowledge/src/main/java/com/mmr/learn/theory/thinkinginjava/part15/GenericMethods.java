package com.mmr.learn.theory.thinkinginjava.part15;

/**
 * 一个基础的泛型方法示例
 * @author Charles Wesley
 * @date 2019/11/20 23:29
 */
public class GenericMethods {
    public <T> void f(T x){
        System.out.println(x.getClass().getName());
    }

    public static void main(String[] args) {
        GenericMethods gm = new GenericMethods();
        gm.f("");
        gm.f(1);
        gm.f(1.0);
        gm.f(1.0F);
        gm.f('c');
        gm.f(gm);
    }
}
