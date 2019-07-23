package com.mmr.learn.theory.thinkinginjava.part5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1,2,3,4);

        Collection<Integer> collection  = new ArrayList<Integer>(list);
        collection.add(5);
        collection.forEach(System.out::println);
    }
}
