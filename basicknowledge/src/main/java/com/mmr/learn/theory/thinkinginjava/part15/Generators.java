package com.mmr.learn.theory.thinkinginjava.part15;

import net.mindview.util.Generator;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 用于Generator的泛型方法
 * 利用生成器Generator，我们可以很方便的填充一个Collection
 * @author Charles Wesley
 * @date 2019/11/24 16:38
 */
public class Generators {
    /**
     *
     * @param coll 待填充的集合
     * @param gen  数据生成器
     * @param n    本次填充多少个元素
     * @param <T>  元素的类型
     * @return     填充好的集合 (返回的是引用，而非一个新集合)
     */
    public static <T>Collection<T> fill(Collection<T> coll, Generator<T> gen, int n){
        for(int i=0; i<n; i++){
            coll.add(gen.next());
        }
        return coll;
    }

    public static void main(String[] args) {
        /*
         * 由于使用了泛型，fill()方法可以根据入参的类型来推断，到底返回什么类型的集合
         */

        //new ArrayList<Coffee>中的Coffee甚至都不用写，因为编译器通过分析上下文(观察Generator<T>中的T的类型)，已经可以得出ArrayList的元素类型必须沙比Coffee
        Collection<Coffee> coffee = fill(new ArrayList<Coffee>(), new CoffeeGenerator(), 4);
        for(Coffee c : coffee){
            System.out.println(c);
        }

        Collection<Integer> fnumbers = fill(new ArrayList<>(), new Fibonacci(), 12);
        for(Integer num : fnumbers){
            System.out.print(num + " ");
        }
    }
}
