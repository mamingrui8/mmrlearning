package com.mmr.learn.theory.thinkinginjava.part15.practice9;

/**
 * 修改GenericMethods.java类，使f()可以接受三个类型各不相同的参数
 * @author Charles Wesley
 * @date 2019/11/20 23:36
 */
public class GenericMethods {
    public <T, U, K> void f(T x, U y, K z){
        System.out.println("x: " + x.getClass().getName());
        System.out.println("y: " + y.getClass().getName());
        System.out.println("z: " + z.getClass().getName());
    }

    public static void main(String[] args) {
        GenericMethods gm = new GenericMethods();
        gm.f('c', 5, 1.0);
    }
}
