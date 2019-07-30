package com.mmr.learn.theory.thinkinginjava.part5.practice19;

public class Main {
    static void test(String... ss){
        for(String s:ss){
            System.out.println(s);
        }
    }

    public static void main(String[] args) {
        Main.test("1", "李", "王");
        Main.test(new String[]{"1", "紅", "綠"});
    }
}
