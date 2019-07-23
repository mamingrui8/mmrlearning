package com.mmr.learn.theory.thinkinginjava.part11.practice.practice11;

import com.mmr.learn.theory.source.java.util.Collection;

import java.util.Iterator;

public class Main {
    private void iterator(Collection collection){
        Iterator it = collection.iterator();
        while(it.hasNext()){
            System.out.println(it.next());
        }
    }

}
