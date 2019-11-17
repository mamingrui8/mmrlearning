package com.mmr.learn.theory.thinkinginjava.part15;

import java.util.ArrayList;
import java.util.Random;

/**
 * 这是一个持有任意类型对象的列表，每次调用select()时，它可以随机地选取一个元素。
 * @author Charles Wesley
 * @date 2019/11/17 21:23
 */
public class RandomList<T> {
    private ArrayList<T> storage = new ArrayList<T>();
    private Random random = new Random(47);
    public void add(T item){
        storage.add(item);
    }
    public T select(){
        return storage.get(random.nextInt(storage.size()));
    }

    public static void main(String[] args) {
        RandomList<String> rs = new RandomList<>();
        for(String s: "The quick start map void".split(" ")){
            rs.add(s);
        }
        for(int i=0;i<5; i++){
            System.out.println(rs.select() + " ");
        }
    }
}
