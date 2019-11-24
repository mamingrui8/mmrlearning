package com.mmr.learn.theory.thinkinginjava.part15;

import java.util.*;

/**
 * Set实用的工具类
 * @author Charles Wesley
 * @date 2019/11/24 18:57
 */
public class Sets {
    public static <T> Set<T> union(Set<T> a, Set<T> b){
        Set<T> result = new HashSet<T>(a);
        result.addAll(b);
        return result;
    }

    public static <T> Set<T> intersection(Set<T> a, Set<T> b){
        Set<T> result = new HashSet<T>(a);
        result.retainAll(b);
        return result;
    }

    /**
     * @return 从superset中移除subset包含的元素
     */
    public static <T> Set<T> difference(Set<T> superset, Set<T> subset){
        Set<T> result = new HashSet<>(superset);
        result.removeAll(subset);
        return result;
    }

    public static <T> Set<T> complement(Set<T> a, Set<T> b){
        return difference(union(a, b), intersection(a, b));
    }
}
