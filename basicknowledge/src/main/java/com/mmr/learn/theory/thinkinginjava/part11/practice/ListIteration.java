package com.mmr.learn.theory.thinkinginjava.part11.practice;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * 对ListIterator的演示
 */
public class ListIteration {
    public static void main(String[] args) {
        List<Pet> pets = Pets.arrayList(8);
        //ListIterator只服务于List集合，不仅能向后遍历数据，还能向前遍历数据
        ListIterator<Pet> it = pets.listIterator();
        while(it.hasNext()){
            System.out.print(it.next() + ", " + it.nextIndex() + ", " + it.previousIndex() + "; ");
        }
        System.out.println();

        //向前遍历元素
        while(it.hasPrevious()){
            System.out.print(it.previous().id() + " ");
        }
        System.out.println();

        System.out.println(pets);
        //可以规定迭代器最初迭代的下标 (从0开始 int类型)
        it = pets.listIterator(3);
        while(it.hasNext()){
            it.next();
            it.set(Pets.randomPet()); //对当前遍历到的元素进行覆盖(替换)操作
        }
        System.out.println(pets);
    }
}
