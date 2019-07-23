package com.mmr.learn.theory.thinkinginjava.part11.practice.practice12;

import java.util.*;
public class Main {
    public static void main(String[] args) {
        List<Integer> list1 = new ArrayList<>();
        Collections.addAll(list1, 1, 2 , 3, 4);
        List<Integer> list2 = new ArrayList<>();

        ListIterator<Integer> it = list1.listIterator(list1.size());
        while(it.hasPrevious()){
            Integer integer = it.previous();
            list2.add(integer);
        }

        list2.forEach(System.out::println);

        list2.clear();
        it = list1.listIterator(

        );
        while(it.hasNext()){
            Integer integer = it.next();
            list2.add(integer);
        }
        list2.forEach(System.out::println);
    }
}
