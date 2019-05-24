package com.mmr.learn.jdk8.t4;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Java8 内置四大核心函数式接口
 *
 * Consumer<T>: 消费型接口
 *       void accept(T t);
 *
 * Supplier<T>: 供给型接口
 *       T get();
 *
 * Function<T, R>: 函数型接口
 *       R apply(T t);
 *
 * Predicate<T>: 断言型接口
 *       boolean test(T t);
 */
public class Test {

    /**
     * Predicate<T> 断言型接口
     */
    @org.junit.Test
    public void test3(){
        List<String> targetList = Arrays.asList("Hello", "Java", "World");
        List<String> strings = filterStr(targetList, str -> str.length() == 5);
        strings.forEach(System.out::println);
    }

    //满足条件的字符串放入集合中
    public List<String> filterStr(List<String> stringList, Predicate<String> predicate){
        List<String> list = new ArrayList<>();
        stringList.forEach(str -> {
            if(predicate.test(str)){
                list.add(str);
            }
        });
        return list;
    }


    /**
     * Consumer<T> 消费型接口
     */
    @org.junit.Test
    public void test(){
        shopping(5000, x -> System.out.println(x));
    }

    private void shopping(double money, Consumer<Double> consumer){
        consumer.accept(money);
    }

    /**
     * 供给型接口
     */
    @org.junit.Test
    public void test1(){
        List<Integer> integerList = getNumberList(10, () -> (int)(Math.random() * 100));
        integerList.forEach(System.out::println);
    }

    /**
     * 获取指定个数的指定类型数据集合
     */
    private List<Integer> getNumberList(Integer nums, Supplier<Integer> supplier){
        List<Integer> integerList = Lists.newArrayList();
        for(int i=0; i< nums; i++){
            int target = supplier.get();
            integerList.add(target);
        }
        return integerList;
    }

    /**
     * 函数型接口
     */
    @org.junit.Test
    public void test2(){
        //去除字符串中所有的空格
        String str1 = handlerStr("我爱你    祖国！", s -> s.replaceAll(" ", ""));
        System.out.println(str1);
        //获取第2至第4个字符
        String str2 = handlerStr("我爱你 祖国！", s-> s.substring(2,5));
        System.out.println(str2);
    }

    /**
     * 字符串处理
     */
    private String handlerStr(String str, Function<String, String> func){
        return func.apply(str);
    }
}
