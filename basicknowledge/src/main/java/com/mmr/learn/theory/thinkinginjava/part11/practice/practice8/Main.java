package com.mmr.learn.theory.thinkinginjava.part11.practice.practice8;

import com.mmr.learn.theory.thinkinginjava.part11.practice.practice1.Gerbil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Gerbil> gerbilList = new ArrayList<>();

        for(int i=0; i< 10; i++){
            gerbilList.add(new Gerbil(i));
        }

        Iterator<Gerbil> it =  gerbilList.iterator();
        while(it.hasNext()){
            Gerbil gerbil = it.next();
            gerbil.hop("我正在跳跃");
        }
    }
}
