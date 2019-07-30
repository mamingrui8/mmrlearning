package com.mmr.learn.theory.thinkinginjava.part5.practice14;

public class Main {
    static private String str1 = "I am str1";
    static private String str2;
    static{
        System.out.println("static execute");
        str2 = "I am str2";
    }

    private int age;
    {
        age = 14;
    }

    static public void print(){
        System.out.println("开始打印");
        System.out.println(str1);
        System.out.println(str2);
    }

    public static void main(String[] args) {
        Main.print();
    }
}
