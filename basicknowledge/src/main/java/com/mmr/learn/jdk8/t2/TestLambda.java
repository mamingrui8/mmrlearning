package com.mmr.learn.jdk8.t2;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 一.
 */
public class TestLambda {

    /**
     * 无参数，无返回值
     */
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

    /**
     * 有一个参数，无返回值
     */
    public void test2(){
        Consumer<String> con = (x) -> System.out.println(x);
        con.accept("12321323");
    }

    /**
     * 有两个以上的参数，有返回值，并且Lambda体中有多条语句
     */
    public void test3(){
        Comparator<Integer> com = (x, y) -> {
            System.out.println("语句1");
            System.out.println("语句2");
            return Integer.compare(x, y);
        };
    }

    /**
     * 若有两个以上参数，有返回值，但Lambda体中只有一条语句
     */
    public void test4(){
        Comparator<Integer> com = (x,y) -> Integer.compare(x,y);
    }

    public  void test5(){

    }

    public void show(Map<String, Integer> map){

    }

    /**
     * 需求:对一个数进行运算
     */
    public void test6(){
        //乘法
        int result1 = getOperation(1000, (x) -> x * 50);
        System.out.println(result1);

        //加法
        int result2 = getOperation(1000, (y) -> y+200);
    }

    public int getOperation(int number, OperationFun fun){
        return fun.getValue(number);
    }
}

@FunctionalInterface
interface OperationFun{
    int getValue(int number);
}