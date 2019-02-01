package com.mmr.learn.genericity;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: mmr
 * Date: 2018-08-29
 * Time: 16:57
 */
public class Test3 {
    public static void main(String[] args){

    }
    /*
        T原本是随便传入一个类。

        1. min_error为什么会有错?
           因为没人有能够保证，传入的类型T中含有compareTo()方法，所以哪怕是编译器也不会让这种垃圾代码通过编译。
        2. min_correct为什么会正确？
           因为T继承了Compareable，规定只有继承了Compareable的类才能放入min_correct去运算。继承了Compareable，那就必然会有compareTo()方法
        3. 凭什么要用extends而非implements?明明implements也可以实现方法的传承。
        4. 需要注意的是，在Java的继承中，可以根据需要拥有多个接口的超类型，但限定有且只能有一个父类。如果用一个类作为限定它必须是限定列表的第一个。
     */

    /**
     *  【有问题版本】计算数组当中的最小元素并返回
     */
    public static <T> T min_error(T[] a){
        if(a == null || a.length == 0) return null;
        T smallest = a[0];
        for(int i = 1;i< a.length; i++) {
            //if(smallest.compareTo(a[i]) > 0) smallest = a[i]; //【报错】
        }
        return smallest;
    }

    /**
     *  【正确版本】 重点关注: 我们为什么要让T继承Compareable
     */
    public static <T extends Comparable> T min_correct1(T[] a){
        if(a == null || a.length == 0) return null;
        T smallest = a[0];
        for(int i = 1;i< a.length; i++) {
            if(smallest.compareTo(a[i]) > 0) smallest = a[i];
        }
        return smallest;
    }

    /**
     *  那如果我们想要让T继承多个类或者接口呢？ 和简单，只需要用&就行了
     */
    public static <T extends Comparable & Serializable> T min_correct2(T[] a){
        if(a == null || a.length == 0) return null;
        T smallest = a[0];
        for(int i = 1;i< a.length; i++) {
            if(smallest.compareTo(a[i]) > 0) smallest = a[i];
        }
        return smallest;
    }

    /**
     *  本案例说明了泛型需要类约束的场景
     *  注意: 类超类型必须是限定列表的第一个，否则必然报错。(Inteface expected here)
     */
    public static <T extends Test123 & Comparable & Serializable> T min_correct3(T[] a){
        if(a == null || a.length == 0) return null;
        T smallest = a[0];
        for(int i = 1;i< a.length; i++) {
            if(smallest.compareTo(a[i]) > 0) {
                smallest = a[i];
                smallest.say(i);
            }
        }
        return smallest;
    }
}

class Test123{
    public void say(int i){System.out.println(i);}
}
