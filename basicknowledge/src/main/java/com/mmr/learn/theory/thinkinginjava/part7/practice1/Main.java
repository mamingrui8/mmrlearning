package com.mmr.learn.theory.thinkinginjava.part7.practice1;

public class Main {
    private Simple simple;

    public static void main(String[] args) {
        Main main = new Main();
        if(main.simple == null){
            main.simple = new Simple();
        }
        System.out.println(main);
    }
}