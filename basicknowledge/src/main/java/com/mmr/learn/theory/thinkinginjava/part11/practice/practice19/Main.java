package com.mmr.learn.theory.thinkinginjava.part11.practice.practice19;


import java.util.*;

public class Main {
    public static void main(String[] args) {
        Set<Pet> petSet = new HashSet<Pet>();
        petSet.add(new Cat("Molly"));
        petSet.add(new Dog("Ginger"));
        petSet.add(new Hamster("Bosco"));

        //对Set进行排序时，首先要转换成List，因为Set本身是无序的，而Collections.sort(List list, Comparator<? extends T> c)  第一个参数必须是List类型
        List<Pet> petList = new ArrayList<>(petSet);
        Collections.sort(petList, new Comparator<Pet>(){
            @Override
            public int compare(Pet o1, Pet o2){
                return o1.compareTo(o2);
            }
        });

        Set<Pet> set = new LinkedHashSet<>(petList);

        System.out.println(set);
    }
}
