package com.mmr.learn.jdk8.t14;

import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        String str = "I am iron man";

        Character[] characters = str
                .chars()
                .mapToObj(c -> (char)c)
                .toArray(Character[]::new);

        str.chars().forEach(System.out::print);

    }
}
