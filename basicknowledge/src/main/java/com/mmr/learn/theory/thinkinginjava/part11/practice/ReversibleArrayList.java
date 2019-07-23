package com.mmr.learn.theory.thinkinginjava.part11.practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class ReversibleArrayList<T> extends ArrayList<T> {
    public ReversibleArrayList(Collection<T> c){
        super(c);
    }

    public Iterable<T> reversed(){
        return new Iterable<T>(){
            public Iterator<T> iterator(){
                return new Iterator<T>() {
                    int current = size() - 1;
                    @Override
                    public boolean hasNext(){
                        return current > -1;
                    }
                    @Override
                    public T next(){
                        return get(current--);
                    }
                    @Override
                    public void remove(){
                        throw new UnsupportedOperationException();
                    }
                };
            }
        };
    }

    /**
     * 下面来看看如何使用能够产生Iterator的方法进行foreach循环
     */
    public static void main(String[] args) {
        ReversibleArrayList<String> rel = new ReversibleArrayList<>(Arrays.asList("To be or not to be".split(" ")));
        //通过正向迭代器进行循环
        for(String s : rel){
            System.out.print(s + " ");
        }
        System.out.println("");

        for(String s : rel.reversed()){
            System.out.println(s + " ");
        }
    }
}
