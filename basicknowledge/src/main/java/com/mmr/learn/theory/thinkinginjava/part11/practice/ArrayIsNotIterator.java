package com.mmr.learn.theory.thinkinginjava.part11.practice;

import java.util.Arrays;

public class ArrayIsNotIterator {
    static <T> void test(Iterable<T> ib){
        for(T t : ib){
            System.out.println(t + " ");
        }
    }

    public static void main(String[] args) {
        test(Arrays.asList(1, 2 ,3));

    }
}
