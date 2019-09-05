package com.mmr.learn.theory.thinkinginjava.part17.t2;

import net.mindview.util.Generator;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 初始化LinkedHashSet的示例
 */
public class CollectionDataTest {
    public static void main(String[] args) {
        Set<String> set = new LinkedHashSet<String>(
                new CollectionData<String>(new Government(), 15)
        );
        set.addAll(CollectionData.list(new Government(), 15));
        System.out.println(set);
    }
}

class Government implements Generator<String> {
    String[] foundation = ("strange women lying in ponds " +
            " xxx " +
            "government").split(" ");
    private int index;
    @Override
    public String next() {
        return foundation[index++];
    }
}