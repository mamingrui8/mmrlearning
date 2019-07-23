package com.mmr.learn.theory.thinkinginjava.part11.practice;

import java.util.*;

public class SetOfInteger {
    public static void main(String[] args) {
        Random rand = new Random(47);
        Set<Integer> intSet = new LinkedHashSet<Integer>();
        for(int i=0; i<10000; i++){
            intSet.add(rand.nextInt(30));
        }
        System.out.println(intSet);
    }
}
