package com.mmr.learn.theory.thinkinginjava.part11.practice;

import java.util.Iterator;

public class IterableClass implements Iterable<String>{
    protected String [] words = "And that is how we know the earth to be banana-shaped.".split(" ");

    @Override
    public Iterator<String> iterator() {
        return new Iterator<String>(){
            private int index = 0;
            @Override
            public String next(){
                return words[index++];
            }

            @Override
            public boolean hasNext(){
                return index < words.length;
            }

            public void remove(){
                throw new UnsupportedOperationException();
            }
        };
    }

    public static void main(String[] args) {
        for(String s : new IterableClass()){
            System.out.print(s + " ");
        }
    }
}
