package com.mmr.learn.theory.thinkinginjava.part5;

public class Flower {
    private int petalCount = 0;
    private String s = "initial value";

    Flower(int petals){
        petalCount = petals;
        System.out.println("传入了int数量作为参数,petalCount = " + petalCount);
    }

    Flower(String ss) {
        System.out.println("传入了String作为参数, s = " + s);
    }

    Flower(String s, int petals){
        this(petals);
        //this(s);  //不能重复调用两次构造器
        this.s = s;
        System.out.println("String & int");
    }

    Flower(){
        this("hi", 47);
    }

    void printPetalCount(){
        //this(petalCount); //不能在非构造器代码块内调用构造器
        System.out.println("petalCount = " + petalCount);
    }

    public static void main(String[] args) {
        Flower x = new Flower();
        x.printPetalCount();
    }
}
