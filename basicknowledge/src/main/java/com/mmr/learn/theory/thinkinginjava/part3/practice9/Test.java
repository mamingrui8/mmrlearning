package com.mmr.learn.theory.thinkinginjava.part3.practice9;

public class Test {
    public static void main(String[] args) {

        //float v = 1e1;  转换失败，高转低时丢失精度需要强制类型转换

        System.out.println(1e2);

        System.out.println("Float MIN: " + Float.MIN_VALUE); // 1.4E-45
        System.out.println("Float MAX: " + Float.MAX_VALUE); // 3.4028235E38

        System.out.println("Double MIN: "+ Double.MIN_VALUE); //4.9E-324
        System.out.println("Double MAX: "+ Double.MAX_VALUE); //1.7976931348623157E308

        int a = 9;
        switch (a){
            case 10: System.out.println("10");
            case 9: System.out.println("9"); break;
            case 8: System.out.println("8"); break;
            default: System.out.println("default");
        }
    }
}
