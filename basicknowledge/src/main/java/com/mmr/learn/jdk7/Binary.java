package com.mmr.learn.jdk7;

/**
 * 二进制
 *
 * @author www.codingme.net
 */
public class Binary {
    public static void main(String[] args) {
        // 二进制 使用0b或者0B开头
        System.out.println("------2进制-----");
        int a = 0b001;
        int b = 0b1010;
        System.out.println(a);
        System.out.println(b);
        // 八进制 使用数字0开头
        System.out.println("------8进制-----");
        int a1 = 010;
        int b1 = 020;
        System.out.println(a1);
        System.out.println(b1);
        // 十六进制 使用数字0x开头
        System.out.println("------16进制-----");
        int a2 = 0x10;
        int b2 = 0x20;
        System.out.println(a2);
        System.out.println(b2);
    }
}
