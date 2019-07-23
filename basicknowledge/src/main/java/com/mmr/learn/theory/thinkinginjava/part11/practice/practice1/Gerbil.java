package com.mmr.learn.theory.thinkinginjava.part11.practice.practice1;

public class Gerbil {
    private int gerbilNumber;

    public Gerbil(int gerbilNumber){
        this.gerbilNumber = gerbilNumber;
    }

    public void hop(String message){
        System.out.println("沙袋鼠号码: " + gerbilNumber + ", 跳跃信息: " + message);
    }
}
