package com.mmr.learn.theory.thinkinginjava.part11.practice;

import net.mindview.util.TextFile;

import java.util.Set;
import java.util.TreeSet;

public class UniqueWords {
    public static void main(String[] args) {
        Set<String> words = new TreeSet<>(
                new TextFile("SetOperaions.java", "\\W+")
        );
        System.out.println(words);
    }
}
