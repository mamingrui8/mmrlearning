package com.mmr.learn.theory.thinkinginjava.part15.practice13;

import com.mmr.learn.theory.thinkinginjava.part15.Generators;
import net.mindview.util.Generator;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * 重载Generators的fill()方法，使参数与返回值的类型为Collection的导出类: List,Queue,Set
 * @author Charles Wesley
 * @date 2019/11/24 16:54
 */
public class E13_OverloadedFill extends Generators {
    public static <T> List<T> fill(List<T> coll, Generator<T> gen, int n){
        for(int i=0; i<n; i++){
            coll.add(gen.next());
        }
        return coll;
    }

    public static <T> Queue<T> fill(Queue<T> coll, Generator<T> gen, int n){
        for(int i=0; i<n; i++){
            coll.add(gen.next());
        }
        return coll;
    }

    public static <T> Set<T> fill(Set<T> coll, Generator<T> gen, int n){
        for(int i=0; i<n; i++){
            coll.add(gen.next());
        }
        return coll;
    }

    /**
     * 是否能够在重载fill()方法使区分List和LinkedList呢
     * 答案是可以的。由于LinkedList同时实现了List和Queue,因此在返回时既可以写List，也可以写Queue
     */
    public static <T> LinkedList<T> fill(LinkedList<T> coll, Generator<T> gen, int n){
        for(int i=0; i<n; i++){
            coll.add(gen.next());
        }
        return coll;
    }
}
