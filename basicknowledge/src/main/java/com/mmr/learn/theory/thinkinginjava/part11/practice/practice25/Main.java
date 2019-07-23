package com.mmr.learn.theory.thinkinginjava.part11.practice.practice25;

import net.mindview.util.TextFile;

import java.util.*;
public class Main {
    public static void main(String[] args) {
        Map<String, ArrayList<Integer>> map = new HashMap<>();
        TextFile strings = new TextFile("C:\\platform\\work\\workspace\\personal\\mmrlearning\\basicknowledge\\src\\main\\java\\com\\mmr\\learn\\theory\\thinkinginjava\\part11\\practice\\UniqueWords.java",
                "\\W+");
        for(int i=0; i< strings.size(); i++){
            String word = strings.get(i); //本次循环得到的单词
            ArrayList<Integer> integers = map.get(word); //该单词出现的位置
            if(integers == null){
                final int number = i;
                map.put(word, new ArrayList<Integer>(){{
                    add(number);
                }});
            }else{
                integers.add(i);
            }
        }

        System.out.println(map);

        //按照单词在最初的文件中出现的顺序进行排序
        map.entrySet().stream()
                .sorted((e1, e2) -> e1.getValue().get(0).compareTo(e2.getValue().get(0)))
                .forEachOrdered(e -> map.put(e.getKey(), e.getValue()));

        System.out.println(map);
    }
}
