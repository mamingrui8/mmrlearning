package com.mmr.learn.theory.thinkinginjava.part7;

/**
 * 继承与初始化顺序
 */
public class Beetle extends Insect{
    private int k = printInit("Beetle.k initialized");
    public Beetle(){
        super("mamingrui");
        System.out.println("k=" + k);
        System.out.println("j=" + j);
    }

    public static int ppp = 10;

    private static int x2 = printInit("static Beetle.x1 initialized");

    public static void main(String[] args) {
        System.out.println("Beetle constructor");

        //Beetle b = new Beetle();

        System.out.println(Beetle.ppp);
    }
}

/**
 * 基类
 */
class Insect{
    private int i = 9;
    private int p = printInit("pppp");
    protected int j;
    Insect(){
        System.out.println("i = " + i + ", j = " + j);
    }

    Insect(String name){
        System.out.println(name);
    }

    private static int x1 = printInit("static Insect.x1 initialized");

    static int printInit(String s){
        System.out.println(s);
        return 47;
    }
}
