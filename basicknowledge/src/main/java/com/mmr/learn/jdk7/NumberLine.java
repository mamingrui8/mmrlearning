package com.mmr.learn.jdk7;

/**
 * jdk7以后，数字定义时可以使用下划线来分割，增加了数字的可读性
 * @author mamr
 * @date 2020/2/29 8:07 下午
 */
public class NumberLine {
    public static void main(String[] args) {
        int a = 1_000;
        int b = 1_0__0_0_0________00;
        System.out.println(a);
        System.out.println(b);
    }
}
