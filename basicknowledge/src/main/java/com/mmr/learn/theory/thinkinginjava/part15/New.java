package com.mmr.learn.theory.thinkinginjava.part15;

import java.util.*;

/**
 * 工具类，包含各种各样的static方法，专门用来创建各种常用的容器对象
 * @author Charles Wesley
 * @date 2019/11/20 23:42
 */
public class New {
    public static <K, V> Map<K, V> map(){
        return new HashMap<>();
    }

    public static <T> List<T> list(){
        return new ArrayList<>();
    }

    public static <T> LinkedList<T> linkedList(){
        return new LinkedList<>();
    }

    public static <T> Set<T> set(){
        return new HashSet<>();
    }

    public static <T> Queue<T> queue(){
        return new LinkedList<>();
    }

    public static void main(String[] args) {
        Map<String, List<String>> sls = New.map();
        List<String> ls = New.list();
        Queue<String> qs = New.queue();
    }
}
