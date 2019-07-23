package com.mmr.learn.theory.thinkinginjava.part11.practice;

import java.util.*;

public class PrintContainers {
    static Collection fill(Collection<String> collection){
        collection.add("rat");
        collection.add("cat");
        collection.add("dog");
        collection.add("dog");
        return collection;
    }

    static Map fill(Map<String, String> map){
        map.put("rat", "Fuzzy");
        map.put("cat", "Rags");
        map.put("dog", "Bosco");
        map.put("dog", "Spot");
        return map;
    }

    public static void main(String[] args) {
        System.out.println("ArrayList: " + fill(new ArrayList<>()));
        System.out.println("LinkedList: " + fill(new LinkedList<>()));
        System.out.println("HashMap: " + fill(new HashMap<>()));
        System.out.println("TreeSet: " + fill(new TreeSet<>())); //按照比较结果的升序来排序
        System.out.println("LinkedHashSet: " + fill(new LinkedHashSet<>())); //按照添加的顺序保存对象
        System.out.println("HashMap: " + fill(new HashMap<>()));
        System.out.println("TreeMap: " + fill(new TreeMap<>()));
        System.out.println("LinkedHashMap: " + fill(new LinkedHashMap<>()));
    }
}
