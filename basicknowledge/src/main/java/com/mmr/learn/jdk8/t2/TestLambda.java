package com.mmr.learn.jdk8.t2;

import java.util.function.Consumer;

/**
 * 一.
 */
public class TestLambda {

    public void test1(){
       Runnable runnable1 = new Runnable() {
           @Override
           public void run() {
               System.out.println("Hello World!");
           }
       };

       System.out.println("--------------");

       Runnable runnable2 = () ->  System.out.println("Hello Lambda!");
    }

    public void test2(){
        Consumer<String> con = (x) -> System.out.println(x);
        con.accept("12321323");
    }

}
