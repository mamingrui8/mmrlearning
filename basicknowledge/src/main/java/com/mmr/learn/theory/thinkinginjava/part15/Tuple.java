package com.mmr.learn.theory.thinkinginjava.part15;

import net.mindview.util.*;

/**
 * 元组工具类
 * @author Charles Wesley
 * @date 2019/11/24 17:44
 */
public class Tuple {
    public static <A, B> TwoTuple<A, B> tuple(A a, B b){
        return new TwoTuple<A,B>(a,b);
    }

    public static <A, B, C>ThreeTuple<A, B, C> tuple(A a,B b, C c){
        return new ThreeTuple<A, B, C>(a, b, c);
    }

    public static <A, B, C, D> FourTuple<A, B, C, D> tuple(A a, B b, C c, D d){
        return new FourTuple<A, B, C, D>(a, b, c, d);
    }
}
