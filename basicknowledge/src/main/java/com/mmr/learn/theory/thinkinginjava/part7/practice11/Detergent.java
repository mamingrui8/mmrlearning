package com.mmr.learn.theory.thinkinginjava.part7.practice11;

public class Detergent {
    private Cleanser cleanser = new Cleanser();

    public void append(String a){
        cleanser.append(a);
    }

    public static void main(String[] args) {
        Detergent detergent = new Detergent();
        detergent.append("test");
    }
}

class Cleanser {
    private String s = "Cleanser";
    public void append(String a){
        s += a;
    }
}