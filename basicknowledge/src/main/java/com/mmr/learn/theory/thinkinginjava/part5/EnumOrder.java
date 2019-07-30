package com.mmr.learn.theory.thinkinginjava.part5;

public class EnumOrder {
    public static void main(String[] args) {
        for(Spiciness ss : Spiciness.values()){
            System.out.println(ss + ".ordinal" + ss.ordinal());
        }
    }
}
