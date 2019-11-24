package com.mmr.learn.theory.thinkinginjava.part15;

import java.util.ArrayList;
import java.util.List;
/**
 * 可变参数与泛型列表 共存
 * @author Charles Wesley
 * @date 2019/11/24 16:27
 */
public class GenericVarags {
    /**
     * makeList()展示了把数组转换成List集合的功能，与java.util.Arrays.asList()方法的功能相同
     */
    public static <T> List<T> makeList(T... args){
        List<T> result = new ArrayList<>();
        for(T item:args){
            result.add(item);
        }
        return result;
    }

    public static void main(String[] args) {
        List<String> ls = makeList("A");
        System.out.println(ls);
        ls = makeList("A", "B", "C");
        System.out.println(ls);
        ls = makeList("ABCSDSDSAEWQE".split(""));
        System.out.println(ls);
    }
}
