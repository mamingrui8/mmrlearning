package com.mmr.learn.theory.thinkinginjava.part11.practice;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试Map的containsKey()和containsValue()方法
 */
public class PetMap {
    public static void main(String[] args) {
        Map<String, Pet> petMap = new HashMap<>();
        petMap.put("My Cat", new Cat("Molly"));
        petMap.put("My Dog", new Dog("Ginger"));
        petMap.put("My hamster", new Hamster("Bosco"));
        System.out.println(petMap);

        Pet dog = petMap.get("My Dog");
        System.out.println(dog);
        System.out.println(petMap.containsKey("My Dog"));
        System.out.println(petMap.containsValue(dog));
    }
}
