package com.mmr.learn.theory.thinkinginjava.part15.practice17;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Charles Wesley
 * @date 2019/11/24 19:44
 */
public class Sets2 {
    public static <T> Set<T> copy(Set<T> a){
        if(a instanceof EnumSet){
            return ((EnumSet) a).clone();
        }
        return new HashSet<>(a);
    }

    public static <T> Set<T> union(Set<T> a, Set<T> b){
        Set<T> result = copy(a);
        result.addAll(b);
        return result;
    }

    public static <T> Set<T> intersection(Set<T> a, Set<T> b){
        Set<T> result = copy(a);
        result.retainAll(b);
        return result;
    }

    /**
     * @return 从superset中移除subset包含的元素
     */
    public static <T> Set<T> difference(Set<T> superset, Set<T> subset){
        Set<T> result = copy(superset);
        result.removeAll(subset);
        return result;
    }

    public static <T> Set<T> complement(Set<T> a, Set<T> b){
        return difference(union(a, b), intersection(a, b));
    }
}
