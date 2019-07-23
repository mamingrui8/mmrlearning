package com.mmr.learn.theory.thinkinginjava.part11.practice.practice18;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Map<String, Pet> petMap = new HashMap<>();
        petMap.put("My Cat", new Cat("Molly"));
        petMap.put("My Dog", new Dog("Ginger"));
        petMap.put("My hamster", new Hamster("Bosco"));

        for(String name : petMap.keySet()){
            System.out.println("键: " + name + ", hash值: " + name.hashCode() + "值: " + petMap.get(name));
        }

        System.out.println();

        System.out.println(petMap.keySet());
        System.out.println(petMap.values());
        System.out.println(petMap.entrySet());

        Map<String ,Pet> linkedMap = new LinkedHashMap<>();
        //按照键进行排序 - 方法1
        petMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEachOrdered(x -> linkedMap.put(x.getKey(), x.getValue()));
        System.out.println(linkedMap);

        //按照键进行排序 - 方法2
        List<Map.Entry<String, Pet>> list = new ArrayList<>(petMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Pet>>(){
            @Override
            public int compare(Map.Entry<String, Pet> o1, Map.Entry<String, Pet> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });
        System.out.println(list);
    }
}
