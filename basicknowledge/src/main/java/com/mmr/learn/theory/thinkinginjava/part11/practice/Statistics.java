package com.mmr.learn.theory.thinkinginjava.part11.practice;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Statistics {
    public static void main(String[] args) {
        Random random = new Random(47);
        Map<Integer, Integer> m = new HashMap<>();
        for(int i=0; i<10000; i++){
            int r = random.nextInt(20);
            Integer freq = m.get(r);
            m.put(r, freq == null ? 1 : freq + 1); //若不存在键，则值为null而不是0
        }
        System.out.println(m);
    }
}
