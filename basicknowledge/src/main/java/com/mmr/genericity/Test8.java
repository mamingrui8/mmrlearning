package com.mmr.genericity;

import java.lang.reflect.Array;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: mmr
 * Date: 2018-09-10
 * Time: 13:06
 */
public class Test8 {
    /**
     *  疑问:
     *          1. 为什么minmax2()不会报错，而minmax1()会报错？
     */


    /**
     *  不能实例化类型变量
     */

    /**
     *  不能构造泛型数组
     */
    public static <T extends Comparable> T[] minmax(T[] a){
        //T[] mm = new T[2];            //此句会报错
        return null;
    }

    /**
     *  案例一: minmax1报错,minmax2正确
     *  分析:
     *          1. 将Object[]类型的引用赋值给String[]类型的变量时，就会发生ClassCastException异常
     *          2. minmax2中，由于利用了Array.newInstance(class, length)，因此
     */
    public static <T> T[] minmax1(T[] a){
        Object[] mm = new Object[2];
        return (T[]) mm;
    }

    public static <T> T[] minmax2(T... a){
        System.out.println(a.getClass().getComponentType());
        T[] mm = (T[])Array.newInstance(a.getClass().getComponentType(), 2);
        return mm;
    }

    public static void main(String[] args){
        String[] ss = minmax2("1","2","3");
    }
}

