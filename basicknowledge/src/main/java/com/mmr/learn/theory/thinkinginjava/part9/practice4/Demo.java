package com.mmr.learn.theory.thinkinginjava.part9.practice4;

public class Demo {
    public static void special(Father top){
        //((Son)top).say();
        top.say();
    }

    public static void main(String[] args) {
        special(new Son());
    }
}


abstract class Father{
    abstract void say();
}

class Son extends Father{
    public void say(){
        System.out.println("Son.say()");
    }
}
