package com.mmr.learn.theory.thinkinginjava.part11.practice.practice20;

import net.mindview.util.TextFile;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        TextFile strings = new TextFile("C:\\platform\\work\\workspace\\personal\\mmrlearning\\basicknowledge\\src\\main\\java\\com\\mmr\\learn\\theory\\thinkinginjava\\part11\\practice\\UniqueWords.java", "\\W+");

        Map<String, Integer> map = new HashMap<>();
        strings.stream()
                .sorted(new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        return o1.compareTo(o2);
                    }
                })
                .forEachOrdered(x -> map.put(x, vowelNumber(x)));
        System.out.println(map);

        //使用String.CASE_INSENSITIVE_ORDER的Collections.sort()方法对结果进行排序

        List<String> entryList = new ArrayList<>(map.keySet());
        entryList.sort(String.CASE_INSENSITIVE_ORDER);

        map.clear();
        entryList.forEach(x -> map.put(x, vowelNumber(x)));
        System.out.println(map);
    }

    private static int vowelNumber(String word){
        Set<Character> characters = new HashSet<>(Arrays.asList('a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U'));
        return (int)word.chars()
                .mapToObj(c -> (char)c)
                .filter(characters::contains)
                .count();
    }
}
