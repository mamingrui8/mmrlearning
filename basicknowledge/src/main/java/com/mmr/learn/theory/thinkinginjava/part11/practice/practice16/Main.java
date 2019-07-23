package com.mmr.learn.theory.thinkinginjava.part11.practice.practice16;

import net.mindview.util.TextFile;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        /*
            计算UniqueWords.java文件中每个单词包含的元音个数
         */
        Set<String> words = new TreeSet<>(
                // \W+ 表示匹配包括下划线在内的任何单词字符。(但不能表示空格)
                new TextFile("C:\\platform\\work\\workspace\\personal\\mmrlearning\\basicknowledge\\src\\main\\java\\com\\mmr\\learn\\theory\\thinkinginjava\\part11\\practice\\UniqueWords.java", "\\W+")
        );
        System.out.println(words);
        Map<String, Integer> map = new LinkedHashMap<>();
        words.forEach(e ->
            map.put(e, vowelNumber(e))
        );
        System.out.println(map);

        /*
            根据键盘输入，返回本次输入单词中元音的个数
         */
        Scanner scanner = new Scanner(System.in);
        while(true){
            String myWord = scanner.nextLine();
            System.out.println(myWord + " : " + vowelNumber(myWord));
        }
    }

    private static int vowelNumber(String word){
        Set<Character> characters = new HashSet<>(Arrays.asList('a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U'));
        return (int)word.chars()
                .mapToObj(c -> (char)c)
                .filter(characters::contains)
                .count();
    }
}
