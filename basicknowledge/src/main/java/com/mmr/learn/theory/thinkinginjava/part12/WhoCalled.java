package com.mmr.learn.theory.thinkinginjava.part12;

import java.util.Stack;

public class WhoCalled {
    static void f(){
        try {
            throw new Exception();
        }catch (Exception e){
            for(StackTraceElement ste : e.getStackTrace()){
                System.out.println(ste.getMethodName());
            }
        }
    }

    static void g() { f(); }
    static void h() { g(); }

    public static void main(String[] args){
        //f();
//        System.out.println("---------------------");
//        g();
//        System.out.println("---------------------");
//        h();
        k();
    }

    static void k(){
        Stack<Integer> stacks = new Stack<>();
        stacks.push(5);
        stacks.push(10);
        stacks.push(4);

        for(Integer i : stacks){
            System.out.println(i);
        }
    }
}
