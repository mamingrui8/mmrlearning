package com.mmr.learn.theory.thinkinginjava.part11.practice.practice1;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Gerbil> gerbilList = new ArrayList<>();

        for(int i=0; i< 10; i++){
            gerbilList.add(new Gerbil(i));
        }

        for(int i=0;i<10;i++){
            gerbilList.get(i).hop("我正在跳跃");
        }
    }
}
